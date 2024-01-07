package com.marinj.shoppingwarfare.feature.history.list.di

import com.marinj.shoppingwarfare.feature.history.list.data.datasource.HistoryDao
import com.marinj.shoppingwarfare.feature.history.list.data.datasource.HistoryDaoImpl
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
        dao: HistoryDaoImpl,
    ): HistoryDao = dao
}
