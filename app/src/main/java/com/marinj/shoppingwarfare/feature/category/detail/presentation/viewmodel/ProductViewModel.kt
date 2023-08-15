package com.marinj.shoppingwarfare.feature.category.detail.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import arrow.core.getOrElse
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.base.TIMEOUT_DELAY
import com.marinj.shoppingwarfare.core.navigation.NavigationEvent.NavigateUp
import com.marinj.shoppingwarfare.core.navigation.Navigator
import com.marinj.shoppingwarfare.core.result.foldToString
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.AddToCart
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.CreateProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.DeleteProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.ObserveProducts
import com.marinj.shoppingwarfare.feature.category.detail.presentation.mapper.ProductToCartItemMapper
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailViewState
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.ProductEvent.OnBackClicked
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
    private val navigator: Navigator,
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
            OnBackClicked -> viewModelScope.launch { navigator.emitDestination(NavigateUp) }
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
        createProduct(categoryId, categoryName, productName).fold(
            ifLeft = { _viewEffect.send(Error("Could not create $productName, try again later.")) },
            ifRight = { Timber.d("Product created with: $categoryId and $productName") },
        )
    }

    private fun handleRestoreProductDeletion(product: Product) = viewModelScope.launch {
        handleCreateCategoryProduct(
            categoryId = product.categoryId.value,
            categoryName = product.categoryName.value,
            productName = product.name.value,
        )
    }

    private fun handleProductDeletion(product: Product) = viewModelScope.launch {
        deleteProduct(productId = product.id.value).fold(
            ifLeft = { _viewEffect.send(Error("Could not delete ${product.name}, try again later.")) },
            ifRight = { _viewEffect.send(ProductDeleted(product)) },
        )
    }

    private fun handleProductClicked(product: Product) = viewModelScope.launch {
        val cartItem = productToCartItemMapper.map(product).getOrElse { failure ->
            _viewEffect.send(Error(failure.foldToString()))
            return@launch
        }
        addToCart(cartItem).fold(
            ifLeft = { _viewEffect.send(Error("Could not add ${product.name} to Cart, try again later.")) },
            ifRight = { _viewEffect.send(AddedToCart(product)) },
        )
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _viewState.update { viewState -> viewState.copy(isLoading = isLoading) }
    }
}
