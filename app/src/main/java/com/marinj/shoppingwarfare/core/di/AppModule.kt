package com.marinj.shoppingwarfare.core.di

import com.marinj.shoppingwarfare.core.dispatcher.DispatcherProvider
import com.marinj.shoppingwarfare.core.navigation.Navigator
import com.marinj.shoppingwarfare.core.navigation.NavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNavigator(
        navigatorImpl: NavigatorImpl,
    ): Navigator = navigatorImpl

    @Provides
    @Singleton
    fun provideDispatcherProvider() = DispatcherProvider(
        io = Dispatchers.IO,
        main = Dispatchers.Main,
        computation = Dispatchers.Default,
    )
}
