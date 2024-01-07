package com.marinj.shoppingwarfare.core.di

import android.content.Context
import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.marinj.shoppingwarfare.db.Database
import com.marinj.shoppingwarfare.db.LocalCategory
import com.marinj.shoppingwarfare.db.LocalHistoryItem
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
    ): Database {
        val driver = AndroidSqliteDriver(
            schema = Database.Schema,
            context = context,
            name = "shopping-warfare-delight.db",
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
