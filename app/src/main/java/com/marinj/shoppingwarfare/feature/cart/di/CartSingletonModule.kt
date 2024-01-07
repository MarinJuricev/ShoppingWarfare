package com.marinj.shoppingwarfare.feature.cart.di

import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDaoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CartSingletonModule {

    @Provides
    @Singleton
    fun provideCartDao(
        dao: CartDaoImpl,
    ): CartDao = dao
}
