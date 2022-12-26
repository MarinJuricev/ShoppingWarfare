package com.marinj.shoppingwarfare.feature.category.list.domain.usecase

import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface ObserveCategories {

    operator fun invoke(): Flow<List<Category>>
}
