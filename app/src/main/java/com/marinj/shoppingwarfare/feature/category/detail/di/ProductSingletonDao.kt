package com.marinj.shoppingwarfare.feature.category.detail.di

import com.marinj.shoppingwarfare.core.data.ShoppingWarfareDatabase
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.local.ProductDao
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.local.CategoryDao
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.local.CategoryDaoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductSingletonDao {

    @Provides
    @Singleton
    fun provideProductDao(
        database: ShoppingWarfareDatabase,
    ): ProductDao = database.productDao()
}
