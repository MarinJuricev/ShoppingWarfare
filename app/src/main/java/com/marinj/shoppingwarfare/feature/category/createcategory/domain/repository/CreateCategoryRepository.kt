package com.marinj.shoppingwarfare.feature.category.createcategory.domain.repository

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.list.domain.model.Category

interface CreateCategoryRepository {
    suspend fun createCategory(category: Category): Either<Failure, Unit>
}
