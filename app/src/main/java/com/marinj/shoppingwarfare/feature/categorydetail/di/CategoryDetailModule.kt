package com.marinj.shoppingwarfare.feature.categorydetail.di

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.categorydetail.data.mapper.DomainToLocalCategoryItemMapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.mapper.LocalCategoryProductsListToDomainProductMapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryProducts
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalProduct
import com.marinj.shoppingwarfare.feature.categorydetail.data.repository.CategoryDetailRepositoryImpl
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.Product
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import com.marinj.shoppingwarfare.feature.categorydetail.presentation.mapper.ProductToCartItemMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CategoryDetailModule {

    @Provides
    fun providesCategoryDetailRepository(
        repository: CategoryDetailRepositoryImpl,
    ): CategoryDetailRepository = repository

    @Provides
    fun providesDomainToLocalCategoryItemMapper(
        mapper: DomainToLocalCategoryItemMapper,
    ): Mapper<LocalProduct, Product> = mapper

    @Provides
    fun providesLocalCategoryProductsListToDomainProductMapper(
        mapper: LocalCategoryProductsListToDomainProductMapper,
    ): Mapper<List<Product>, List<LocalCategoryProducts>> = mapper

    @Provides
    fun providesProductToCartItemMapper(
        mapper: ProductToCartItemMapper,
    ): Mapper<CartItem, Product> = mapper
}
