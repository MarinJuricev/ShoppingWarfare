package com.marinj.shoppingwarfare.feature.category.createcategory.di

import com.marinj.shoppingwarfare.feature.category.createcategory.domain.repository.CreateCategoryRepository
import com.marinj.shoppingwarfare.feature.category.createcategory.data.repository.CreateCategoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class CreateCategoryModule {

    @Binds
    @ViewModelScoped
    abstract fun provideCreateCategoryRepository(
        repository: CreateCategoryRepositoryImpl
    ): CreateCategoryRepository
}
