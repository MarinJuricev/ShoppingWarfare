package com.marinj.shoppingwarfare.feature.cart.di

import com.marinj.shoppingwarfare.feature.cart.data.repository.CartRepositoryImpl
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CartModule {

    @Binds
    abstract fun provideCartRepository(
        repository: CartRepositoryImpl
    ): CartRepository
}
