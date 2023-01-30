package com.marinj.shoppingwarfare.feature.category.detail.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.base.TIMEOUT_DELAY
import com.marinj.shoppingwarfare.core.result.Either.Left
import com.marinj.shoppingwarfare.core.result.Either.Right
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.AddToCart
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.CreateProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.DeleteProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.ObserveProducts
import com.marinj.shoppingwarfare.feature.category.detail.presentation.mapper.ProductToCartItemMapper
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailViewState
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.OnCreateProduct
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.OnGetProducts
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.OnProductClicked
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.OnProductDelete
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.RestoreProductDeletion
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductViewEffect
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductViewEffect.AddedToCart
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductViewEffect.Error
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductViewEffect.ProductDeleted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val observeProducts: ObserveProducts,
    private val createProduct: CreateProduct,
    private val deleteProduct: DeleteProduct,
    private val productToCartItemMapper: ProductToCartItemMapper,
    private val addToCart: AddToCart,
) : BaseViewModel<ProductEvent>() {

    private val _viewState = MutableStateFlow(CategoryDetailViewState())
    val viewState = _viewState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_DELAY),
        initialValue = CategoryDetailViewState(),
    )

    private val _viewEffect = Channel<ProductViewEffect>(Channel.BUFFERED)
    val viewEffect = _viewEffect.receiveAsFlow()

    override fun onEvent(event: ProductEvent) {
        when (event) {
            is OnGetProducts -> handleGetCategoryProducts(event.categoryId)
            is OnCreateProduct -> handleCreateCategoryProduct(
                event.categoryId,
                event.categoryName,
                event.productName,
            )
            is OnProductDelete -> handleProductDeletion(event.product)
            is RestoreProductDeletion -> handleRestoreProductDeletion(event.product)
            is OnProductClicked -> handleProductClicked(event.product)
        }
    }

    private fun handleGetCategoryProducts(categoryId: String) = viewModelScope.launch {
        observeProducts(categoryId)
            .onStart { updateIsLoading(isLoading = true) }
            .catch { handleGetCategoriesError() }
            .collect { products ->
                _viewState.update { viewState ->
                    viewState.copy(
                        products = products,
                        isLoading = false,
                    )
                }
            }
    }

    private suspend fun handleGetCategoriesError() {
        updateIsLoading(isLoading = false)
        _viewEffect.send(Error("Failed to fetch category items, try again later."))
    }

    private fun handleCreateCategoryProduct(
        categoryId: String,
        categoryName: String,
        productName: String,
    ) = viewModelScope.launch {
        when (createProduct(categoryId, categoryName, productName)) {
            is Right -> Timber.d("Product created with: $categoryId and $productName")
            is Left -> _viewEffect.send(Error("Could not create $productName, try again later."))
        }
    }

    private fun handleRestoreProductDeletion(product: Product) = viewModelScope.launch {
        handleCreateCategoryProduct(
            categoryId = product.categoryId,
            categoryName = product.categoryName,
            productName = product.name,
        )
    }

    private fun handleProductDeletion(product: Product) = viewModelScope.launch {
        when (deleteProduct(product.id)) {
            is Right -> _viewEffect.send(ProductDeleted(product))
            is Left -> _viewEffect.send(Error("Could not delete ${product.name}, try again later."))
        }
    }

    private fun handleProductClicked(product: Product) = viewModelScope.launch {
        val cartItem = productToCartItemMapper.map(product)
        when (addToCart(cartItem)) {
            is Right -> _viewEffect.send(AddedToCart(product))
            is Left -> _viewEffect.send(Error("Could not add ${product.name} to Cart, try again later."))
        }
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _viewState.update { viewState -> viewState.copy(isLoading = isLoading) }
    }
}