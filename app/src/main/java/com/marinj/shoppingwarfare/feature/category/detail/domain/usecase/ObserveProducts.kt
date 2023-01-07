package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveProducts @Inject constructor(
    private val productRepository: ProductRepository,
) {

    operator fun invoke(categoryId: String): Flow<List<Product>> =
        productRepository.observeProducts(categoryId)
}
