package com.marinj.shoppingwarfare.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.util.UUID

@InstallIn(ViewModelComponent::class)
@Module
object CoreDomainModule {

    @Provides
    fun provideUuidGenerator(): () -> String = { UUID.randomUUID().toString() }

    @Provides
    fun provideTimeStamp(): () -> Long = { System.currentTimeMillis() }
}