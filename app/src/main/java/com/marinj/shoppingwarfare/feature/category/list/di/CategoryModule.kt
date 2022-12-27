package com.marinj.shoppingwarfare.feature.category.list.di

import com.marinj.shoppingwarfare.feature.category.list.data.datasource.network.CategoryApi
import com.marinj.shoppingwarfare.feature.category.list.data.datasource.network.CategoryApiImpl
import com.marinj.shoppingwarfare.feature.category.list.data.repository.CategoryRepositoryImpl
import com.marinj.shoppingwarfare.feature.category.list.domain.repository.CategoryRepository
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.DeleteCategory
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.DeleteCategoryImpl
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.ObserveCategories
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.ObserveCategoriesImpl
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.UndoCategoryDeletion
import com.marinj.shoppingwarfare.feature.category.list.domain.usecase.UndoCategoryDeletionImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class CategoryModule {

    @Binds
    abstract fun bindCategoryRepository(
        repository: CategoryRepositoryImpl,
    ): CategoryRepository

    @Binds
    abstract fun bindCategoryApi(
        categoryApiImpl: CategoryApiImpl,
    ): CategoryApi

    @Binds
    abstract fun bindObserveCategories(
        observeCategoriesImpl: ObserveCategoriesImpl,
    ): ObserveCategories

    @Binds
    abstract fun bindDeleteCategory(
        observeCategoriesImpl: DeleteCategoryImpl,
    ): DeleteCategory

    @Binds
    abstract fun bindUndoCategoryDeletion(
        undoCategoryDeletion: UndoCategoryDeletionImpl,
    ): UndoCategoryDeletion
}
