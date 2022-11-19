package com.marinj.shoppingwarfare.feature.category.list.data.datasource.network

import com.marinj.shoppingwarfare.core.exception.SHWException
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.list.data.model.RemoteCategoryItem
import kotlinx.coroutines.flow.Flow

interface CategoryApi {

    @Throws(SHWException::class)
    fun observeCategoryItems(): Flow<List<RemoteCategoryItem>>

    suspend fun addCategoryItem(categoryItem: RemoteCategoryItem): Either<Failure, Unit>

    suspend fun deleteCategoryItem(categoryItem: RemoteCategoryItem): Either<Failure, Unit>
}