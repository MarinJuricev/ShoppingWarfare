package com.marinj.shoppingwarfare.feature.createcategory.di

import com.marinj.shoppingwarfare.feature.createcategory.data.repository.CreateCategoryRepositoryImpl
import com.marinj.shoppingwarfare.feature.createcategory.domain.repository.CreateCategoryRepository
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
