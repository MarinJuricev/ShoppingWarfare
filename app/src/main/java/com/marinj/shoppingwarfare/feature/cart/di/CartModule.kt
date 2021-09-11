package com.marinj.shoppingwarfare.feature.cart.di

import com.marinj.shoppingwarfare.feature.cart.data.repository.CartRepositoryImpl
import com.marinj.shoppingwarfare.feature.cart.domain.repository.CartRepository
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
}
