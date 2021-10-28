package com.marinj.shoppingwarfare.core.di

import com.marinj.shoppingwarfare.core.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideNavigator(
        navigator: Navigator
    ): Navigator = navigator
}
