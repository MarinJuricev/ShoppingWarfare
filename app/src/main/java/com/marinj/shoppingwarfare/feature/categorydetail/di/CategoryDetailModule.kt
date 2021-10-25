package com.marinj.shoppingwarfare.feature.categorydetail.di

import com.marinj.shoppingwarfare.feature.categorydetail.data.repository.CategoryDetailRepositoryImpl
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CategoryDetailModule {

    @Binds
    abstract fun providesCategoryDetailRepository(
        repository: CategoryDetailRepositoryImpl
    ): CategoryDetailRepository
}
