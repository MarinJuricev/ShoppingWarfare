package com.marinj.shoppingwarfare.feature.category.di

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.category.data.mapper.DomainToLocalCategoryMapper
import com.marinj.shoppingwarfare.feature.category.data.mapper.LocalToDomainCategoryMapper
import com.marinj.shoppingwarfare.feature.category.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.category.data.repository.CategoryRepositoryImpl
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.domain.repository.CategoryRepository
import com.marinj.shoppingwarfare.feature.category.presentation.mapper.CategoryToUiCategoryMapper
import com.marinj.shoppingwarfare.feature.category.presentation.model.UiCategory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.util.*

@InstallIn(ViewModelComponent::class)
@Module
object CategoryModule {

    @Provides
    fun provideLocalToDomainCategoryMapper(
        mapper: LocalToDomainCategoryMapper
    ): Mapper<Category, LocalCategory> = mapper

    @Provides
    fun provideDomainToLocalCategoryMapper(
        mapper: DomainToLocalCategoryMapper
    ): Mapper<LocalCategory, Category> = mapper

    @Provides
    fun provideCategoryToUiCategoryMapper(
        mapper: CategoryToUiCategoryMapper
    ): Mapper<UiCategory, Category> = mapper

    @Provides
    fun provideCategoryRepository(
        repository: CategoryRepositoryImpl
    ): CategoryRepository = repository

    @Provides
    fun provideUuidGenerator(): () -> String = { UUID.randomUUID().toString() }
}
