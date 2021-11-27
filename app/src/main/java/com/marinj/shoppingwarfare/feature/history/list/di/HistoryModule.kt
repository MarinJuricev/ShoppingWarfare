package com.marinj.shoppingwarfare.feature.history.list.di

import com.marinj.shoppingwarfare.feature.history.list.data.repository.HistoryRepositoryImpl
import com.marinj.shoppingwarfare.feature.history.list.domain.repository.HistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object HistoryModule {

    @Provides
    @ViewModelScoped
    fun provideHistoryRepository(
        repository: HistoryRepositoryImpl
    ): HistoryRepository = repository
}
