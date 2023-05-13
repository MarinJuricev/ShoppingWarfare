package com.marinj.shoppingwarfare.feature.category.detail.domain.usecase

import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ObserveProducts {
    operator fun invoke(categoryId: String): Flow<List<Product>>
}
