package com.marinj.shoppingwarfare.feature.category.list.data.datasource.network

import com.marinj.shoppingwarfare.core.exception.SHWException
import com.marinj.shoppingwarfare.feature.category.list.data.model.RemoteCategoryItem
import kotlinx.coroutines.flow.Flow

interface CategoryApi {

    @Throws(SHWException::class)
    fun observeCategoryItems(): Flow<List<RemoteCategoryItem>>

    suspend fun addCategoryItem(cartItem: RemoteCategoryItem)
}