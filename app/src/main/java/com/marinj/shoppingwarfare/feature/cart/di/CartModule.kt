package com.marinj.shoppingwarfare.feature.cart.di

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.cart.data.mapper.DomainToLocalCartItemMapper
import com.marinj.shoppingwarfare.feature.cart.data.mapper.LocalToDomainCartItemMapper
import com.marinj.shoppingwarfare.feature.cart.data.model.LocalCartItem
import com.marinj.shoppingwarfare.feature.cart.data.repository.CartRepositoryImpl
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import com.marinj.shoppingwarfare.feature.cart.presentation.mapper.CartItemsToCartDataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object CartModule {

    @Provides
    fun provideCartRepository(
        repository: CartRepositoryImpl
    ): CartRepository = repository

    @Provides
    fun provideLocalToDomainCartItemMapper(
        mapper: LocalToDomainCartItemMapper
    ): Mapper<CartItem, LocalCartItem> = mapper

    @Provides
    fun provideDomainToLocalCartItemMapper(
        mapper: DomainToLocalCartItemMapper
    ): Mapper<LocalCartItem, CartItem> = mapper

    @Provides
    fun provideCartItemsToCartDataMapper(
        mapper: CartItemsToCartDataMapper
    ): Mapper<Map<String, List<CartItem>>, List<CartItem>> = mapper
}
