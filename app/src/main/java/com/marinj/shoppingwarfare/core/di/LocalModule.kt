package com.marinj.shoppingwarfare.core.di

import android.content.Context
import androidx.room.Room
import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.marinj.shoppingwarfare.core.data.ShoppingWarfareDatabase
import com.marinj.shoppingwarfare.core.data.ShoppingWarfareRoomDatabase
import com.marinj.shoppingwarfare.db.Database
import com.marinj.shoppingwarfare.db.LocalCategory
import com.marinj.shoppingwarfare.db.LocalHistoryItem
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
    fun provideRoomDatabase(
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
        database: ShoppingWarfareRoomDatabase,
    ): ShoppingWarfareDatabase = database

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): Database {
        val driver = AndroidSqliteDriver(
            schema = Database.Schema,
            context = context,
            name = "shopping-warfare.db",
        )

        return Database(
            driver = driver,
            LocalCategoryAdapter = LocalCategory.Adapter(
                backgroundColorAdapter = LongColumnAdapter,
                titleColorAdapter = LongColumnAdapter,
            ),
            LocalHistoryItemAdapter = LocalHistoryItem.Adapter(
                timestampAdapter = LongColumnAdapter,
            ),
        )
    }
}

object LongColumnAdapter : ColumnAdapter<Long, Long> {
    override fun decode(databaseValue: Long): Long = databaseValue

    override fun encode(value: Long): Long = value
}
