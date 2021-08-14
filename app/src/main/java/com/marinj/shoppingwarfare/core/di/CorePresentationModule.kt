package com.marinj.shoppingwarfare.core.di

import com.marinj.shoppingwarfare.core.mapper.FailureToStringMapper
import com.marinj.shoppingwarfare.core.mapper.Mapper
import com.marinj.shoppingwarfare.core.result.Failure
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
object CorePresentationModule {

    @Provides
    fun provideFailureToStringMapper(
        mapper: FailureToStringMapper
    ): Mapper<String, Failure> = mapper
}