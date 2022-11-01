package com.marinj.shoppingwarfare.core.di

import android.content.Context
import androidx.room.Room
import com.marinj.shoppingwarfare.core.data.ShoppingWarfareDatabase
import com.marinj.shoppingwarfare.core.data.ShoppingWarfareRoomDatabase
import com.marinj.shoppingwarfare.feature.history.list.data.datasource.HistoryDaoTypeConverters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        historyDaoTypeConverters: HistoryDaoTypeConverters,
    ): ShoppingWarfareRoomDatabase {
        val builder = Room.databaseBuilder(
            context,
            ShoppingWarfareRoomDatabase::class.java,
            "shopping-warfare.db",
        ).addTypeConverter(historyDaoTypeConverters)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideShoppingWarfareDatabase(
        database: ShoppingWarfareRoomDatabase
    ): ShoppingWarfareDatabase = database
}
