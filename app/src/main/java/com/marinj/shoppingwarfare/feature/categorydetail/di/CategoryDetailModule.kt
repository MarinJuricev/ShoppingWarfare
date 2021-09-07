package com.marinj.shoppingwarfare.feature.categorydetail.di

import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.mapper.DomainToLocalCategoryItemMapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.mapper.LocalToDomainCategoryItemMapper
import com.marinj.shoppingwarfare.feature.categorydetail.data.model.LocalCategoryProduct
import com.marinj.shoppingwarfare.feature.categorydetail.data.repository.CategoryDetailRepositoryImpl
import com.marinj.shoppingwarfare.feature.categorydetail.domain.model.CategoryProduct
import com.marinj.shoppingwarfare.feature.categorydetail.domain.repository.CategoryDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface CategoryDetailModule {

    @Binds
    fun bindCategoryDetailRepository(
        repository: CategoryDetailRepositoryImpl,
    ): CategoryDetailRepository

    @Binds
    fun bindDomainToLocalCategoryItemMapper(
        mapper: DomainToLocalCategoryItemMapper,
    ): Mapper<LocalCategoryProduct, CategoryProduct>

    @Binds
    fun bindLocalToDomainCategoryItemMapper(
        mapper: LocalToDomainCategoryItemMapper,
    ): Mapper<CategoryProduct, LocalCategoryProduct>
}
