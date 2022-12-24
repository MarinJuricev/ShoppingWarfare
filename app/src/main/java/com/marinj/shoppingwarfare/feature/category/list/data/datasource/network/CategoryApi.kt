package com.marinj.shoppingwarfare.feature.category.list.data.datasource.network

import com.marinj.shoppingwarfare.core.exception.SHWException
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.list.data.model.RemoteCategory
import kotlinx.coroutines.flow.Flow

interface CategoryApi {

    @Throws(SHWException::class)
    fun observeCategories(): Flow<List<RemoteCategory>>

    suspend fun addCategoryItem(categoryItem: RemoteCategory): Either<Failure, Unit>

    suspend fun deleteCategoryItemById(categoryId: String): Either<Failure, Unit>
}
