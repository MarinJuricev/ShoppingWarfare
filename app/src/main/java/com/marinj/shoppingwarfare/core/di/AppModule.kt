package com.marinj.shoppingwarfare.core.di

import com.marinj.shoppingwarfare.core.dispatcher.DispatcherProvider
import com.marinj.shoppingwarfare.core.navigation.Navigator
import com.marinj.shoppingwarfare.core.navigation.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindNavigator(
        navigatorImpl: NavigatorImpl,
    ): Navigator


    @Provides
    @Singleton
    fun provideDispatcherProvider() = DispatcherProvider(
        io = Dispatchers.IO,
        main = Dispatchers.Main,
        computation = Dispatchers.Default,
    )
}
