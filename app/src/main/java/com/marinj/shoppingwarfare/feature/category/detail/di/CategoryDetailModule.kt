package com.marinj.shoppingwarfare.feature.category.detail.di

import com.marinj.shoppingwarfare.feature.category.detail.data.repository.CategoryDetailRepositoryImpl
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CategoryDetailModule {

    @Binds
    abstract fun providesCategoryDetailRepository(
        repository: CategoryDetailRepositoryImpl,
    ): ProductRepository
}
