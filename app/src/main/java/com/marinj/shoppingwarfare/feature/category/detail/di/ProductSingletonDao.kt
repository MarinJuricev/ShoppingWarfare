package com.marinj.shoppingwarfare.feature.category.detail.di

import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.local.ProductDao
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.local.ProductDaoImpl
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
        productDao: ProductDaoImpl,
    ): ProductDao = productDao
}
