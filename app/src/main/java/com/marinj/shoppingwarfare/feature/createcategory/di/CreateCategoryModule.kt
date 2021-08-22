package com.marinj.shoppingwarfare.feature.createcategory.di

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.createcategory.data.repository.CreateCategoryRepositoryImpl
import com.marinj.shoppingwarfare.feature.createcategory.domain.repository.CreateCategoryRepository
import com.marinj.shoppingwarfare.feature.createcategory.presentation.mapper.FailureToCreateCategoryEffectMapper
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEffect
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object CreateCategoryModule {

    @Provides
    fun provideCreateCategoryRepository(
        repository: CreateCategoryRepositoryImpl
    ): CreateCategoryRepository = repository

    @Provides
    fun provideFailureToCreateCategoryEffectMapper(
        mapper: FailureToCreateCategoryEffectMapper
    ): Mapper<CreateCategoryEffect, Failure> = mapper
}