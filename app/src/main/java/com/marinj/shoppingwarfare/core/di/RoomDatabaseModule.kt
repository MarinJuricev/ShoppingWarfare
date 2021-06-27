package com.marinj.shoppingwarfare.core.di

import android.content.Context
import androidx.room.Room
import com.marinj.shoppingwarfare.core.data.ShoppingWarfareRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): ShoppingWarfareRoomDatabase {
        val builder = Room.databaseBuilder(
            context,
            ShoppingWarfareRoomDatabase::class.java,
            "shopping-warfare.db",
        )

        return builder.build()
    }
}
