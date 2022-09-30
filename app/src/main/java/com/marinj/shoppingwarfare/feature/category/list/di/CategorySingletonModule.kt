package com.marinj.shoppingwarfare.feature.category.list.di

import com.marinj.shoppingwarfare.core.data.ShoppingWarfareDatabase
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.local.CategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CategorySingletonModule {

    @Provides
    @Singleton
    fun provideCategoryDao(
        database: ShoppingWarfareDatabase,
    ): CategoryDao = database.categoryDao()
}
