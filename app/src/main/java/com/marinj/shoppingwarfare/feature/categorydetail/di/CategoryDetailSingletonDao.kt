package com.marinj.shoppingwarfare.feature.categorydetail.di

import com.marinj.shoppingwarfare.core.data.ShoppingWarfareDatabase
import com.marinj.shoppingwarfare.feature.categorydetail.data.datasource.CategoryDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryDetailSingletonDao {

    @Provides
    @Singleton
    fun provideCategoryDetailDao(
        database: ShoppingWarfareDatabase
    ): CategoryDetailDao = database.categoryDetailDao()
}
