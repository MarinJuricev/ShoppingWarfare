package com.marinj.shoppingwarfare.feature.cart.di

import com.marinj.shoppingwarfare.feature.cart.data.remote.CartApi
import com.marinj.shoppingwarfare.feature.cart.data.remote.CartApiImpl
import com.marinj.shoppingwarfare.feature.cart.data.repository.CartRepositoryImpl
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.AddToCart
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.AddToCartImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CartModule {

    @Binds
    abstract fun bindCartRepository(
        repository: CartRepositoryImpl,
    ): CartRepository

    @Binds
    abstract fun bindCartApi(
        cartApiImpl: CartApiImpl,
    ): CartApi

    @Binds
    abstract fun bindAddToCart(
        addToCartImpl: AddToCartImpl,
    ): AddToCart
}
