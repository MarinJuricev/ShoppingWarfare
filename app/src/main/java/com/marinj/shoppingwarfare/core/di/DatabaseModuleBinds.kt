package com.marinj.shoppingwarfare.core.di

import com.marinj.shoppingwarfare.core.data.ShoppingWarfareDatabase
import com.marinj.shoppingwarfare.core.data.ShoppingWarfareRoomDatabase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DatabaseModuleBinds {

    @Binds
    abstract fun bindShoppingWarfareDatabase(
        database: ShoppingWarfareRoomDatabase
    ): ShoppingWarfareDatabase
}
