package com.marinj.shoppingwarfare.feature.history.di

import com.marinj.shoppingwarfare.feature.history.data.repository.HistoryRepositoryImpl
import com.marinj.shoppingwarfare.feature.history.domain.repository.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object HistoryModule {

    @Provides
    fun provideHistoryRepository(
        repository: HistoryRepositoryImpl
    ): HistoryRepository = repository
}
