package com.marinj.shoppingwarfare.feature.category.detail.di

import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.network.ProductApi
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.network.ProductApiImpl
import com.marinj.shoppingwarfare.feature.category.detail.data.repository.ProductRepositoryImpl
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.CreateProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.CreateProductImpl
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.DeleteProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.DeleteProductImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ProductModule {

    @Binds
    abstract fun bindProductRepository(
        repository: ProductRepositoryImpl,
    ): ProductRepository

    @Binds
    abstract fun bindProductApi(
        productApi: ProductApiImpl,
    ): ProductApi

    @Binds
    abstract fun bindCreateProduct(
        createProductImpl: CreateProductImpl,
    ): CreateProduct

    @Binds
    abstract fun bindDeleteProduct(
        deleteProductImpl: DeleteProductImpl,
    ): DeleteProduct
}
