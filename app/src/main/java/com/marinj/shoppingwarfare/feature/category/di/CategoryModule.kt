package com.marinj.shoppingwarfare.feature.category.di

import com.marinj.shoppingwarfare.core.data.ShoppingWarfareDatabase
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.category.data.datasource.CategoryDao
import com.marinj.shoppingwarfare.feature.category.data.mapper.DomainToLocalCategoryMapper
import com.marinj.shoppingwarfare.feature.category.data.mapper.LocalToDomainCategoryMapper
import com.marinj.shoppingwarfare.feature.category.data.model.LocalCategory
import com.marinj.shoppingwarfare.feature.category.data.repository.CategoryRepositoryImpl
import com.marinj.shoppingwarfare.feature.category.domain.model.Category
import com.marinj.shoppingwarfare.feature.category.domain.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object CategoryModule {

    @Provides
    fun provideCategoryDao(
        database: ShoppingWarfareDatabase,
    ): CategoryDao = database.categoryDao()

    @Provides
    fun provideLocalToDomainCategoryMapper(
        mapper: LocalToDomainCategoryMapper
    ): Mapper<Category, LocalCategory> = mapper

    @Provides
    fun provideDomainToLocalCategoryMapper(
        mapper: DomainToLocalCategoryMapper
    ): Mapper<LocalCategory, Category> = mapper

    @Provides
    fun provideCategoryRepository(
        repository: CategoryRepositoryImpl
    ): CategoryRepository = repository
}
