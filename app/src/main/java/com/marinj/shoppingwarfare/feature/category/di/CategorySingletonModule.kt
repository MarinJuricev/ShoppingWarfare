package com.marinj.shoppingwarfare.feature.category.di

import com.marinj.shoppingwarfare.core.data.ShoppingWarfareDatabase
import com.marinj.shoppingwarfare.feature.category.data.datasource.CategoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object CategorySingletonModule {

    @Provides
    fun provideCategoryDao(
        database: ShoppingWarfareDatabase,
    ): CategoryDao = database.categoryDao()
}