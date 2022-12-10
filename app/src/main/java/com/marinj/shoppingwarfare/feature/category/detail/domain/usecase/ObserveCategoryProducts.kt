package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCategoryProducts @Inject constructor(
    private val categoryDetailRepository: ProductRepository,
) {

    operator fun invoke(categoryId: String): Flow<List<Product>> =
        categoryDetailRepository.observeProducts(categoryId)
}
