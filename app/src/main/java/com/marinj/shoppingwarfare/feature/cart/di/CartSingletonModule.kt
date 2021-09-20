package com.marinj.shoppingwarfare.feature.cart.di

import com.marinj.shoppingwarfare.core.data.ShoppingWarfareDatabase
import com.marinj.shoppingwarfare.feature.cart.data.datasource.CartDao
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
        database: ShoppingWarfareDatabase,
    ): CartDao = database.cartDao()
}
