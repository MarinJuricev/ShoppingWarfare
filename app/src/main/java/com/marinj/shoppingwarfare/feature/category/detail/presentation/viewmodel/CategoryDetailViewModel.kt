package com.marinj.shoppingwarfare.feature.category.detail.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.core.ext.safeUpdate
import com.marinj.shoppingwarfare.core.result.Either.Left
import com.marinj.shoppingwarfare.core.result.Either.Right
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.AddToCart
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.CreateProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.DeleteProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.ObserveCategoryProducts
import com.marinj.shoppingwarfare.feature.category.detail.presentation.mapper.ProductToCartItemMapper
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEffect
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEffect.AddedToCart
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEffect.Error
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEffect.ProductDeleted
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEvent
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEvent.OnCreateCategoryProduct
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEvent.OnGetCategoryProducts
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEvent.OnProductClicked
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEvent.OnProductDelete
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailEvent.RestoreProductDeletion
import com.marinj.shoppingwarfare.feature.category.detail.presentation.model.CategoryDetailViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val observeCategoryProducts: ObserveCategoryProducts,
    private val createProduct: CreateProduct,
    private val deleteProduct: DeleteProduct,
    private val productToCartItemMapper: ProductToCartItemMapper,
    private val addToCart: AddToCart,
) : BaseViewModel<CategoryDetailEvent>() {

    private val _viewState = MutableStateFlow(CategoryDetailViewState())
    val viewState = _viewState.asStateFlow()

    private val _viewEffect = Channel<CategoryDetailEffect>()
    val viewEffect = _viewEffect.receiveAsFlow()

    override fun onEvent(event: CategoryDetailEvent) {
        when (event) {
            is OnGetCategoryProducts -> handleGetCategoryProducts(event.categoryId)
            is OnCreateCategoryProduct -> handleCreateCategoryProduct(
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
        updateIsLoading(isLoading = true)
        observeCategoryProducts(categoryId)
            .catch { handleGetCategoriesError() }
            .collect { products ->
                _viewState.safeUpdate(
                    _viewState.value.copy(
                        products = products,
                        isLoading = false,
                    )
                )
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
            productName = product.name
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
        _viewState.safeUpdate(_viewState.value.copy(isLoading = isLoading))
    }
}
