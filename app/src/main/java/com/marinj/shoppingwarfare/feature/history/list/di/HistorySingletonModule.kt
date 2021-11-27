package com.marinj.shoppingwarfare.feature.history.list.di

import com.marinj.shoppingwarfare.core.data.ShoppingWarfareDatabase
import com.marinj.shoppingwarfare.feature.history.list.data.datasource.HistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object HistorySingletonModule {

    @Provides
    @Singleton
    fun provideHistoryDao(
        database: ShoppingWarfareDatabase,
    ): HistoryDao = database.historyDao()
}
