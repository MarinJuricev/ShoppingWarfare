package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveProductsImpl @Inject constructor(
    private val productRepository: ProductRepository,
) : ObserveProducts {

    override operator fun invoke(categoryId: String): Flow<List<Product>> =
        productRepository.observeProducts(categoryId)
}
