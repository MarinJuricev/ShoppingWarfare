package com.marinj.shoppingwarfare.feature.history.list.di

import com.marinj.shoppingwarfare.feature.history.detail.domain.usecase.GetHistoryItemById
import com.marinj.shoppingwarfare.feature.history.detail.domain.usecase.GetHistoryItemByIdImpl
import com.marinj.shoppingwarfare.feature.history.list.data.repository.HistoryRepositoryImpl
import com.marinj.shoppingwarfare.feature.history.list.domain.repository.HistoryRepository
import com.marinj.shoppingwarfare.feature.history.list.domain.usecase.ObserveHistoryItems
import com.marinj.shoppingwarfare.feature.history.list.domain.usecase.ObserveHistoryItemsImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class HistoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindHistoryRepository(
        repositoryImpl: HistoryRepositoryImpl,
    ): HistoryRepository

    @Binds
    @ViewModelScoped
    abstract fun bindObserveHistoryItems(
        observeHistoryItemsImpl: ObserveHistoryItemsImpl,
    ): ObserveHistoryItems

    @Binds
    @ViewModelScoped
    abstract fun bindGetHistoryItemById(
        getHistoryItemByIdImpl: GetHistoryItemByIdImpl,
    ): GetHistoryItemById

}
