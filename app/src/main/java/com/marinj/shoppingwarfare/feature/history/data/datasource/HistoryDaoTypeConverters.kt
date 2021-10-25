package com.marinj.shoppingwarfare.feature.history.data.datasource

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.marinj.shoppingwarfare.feature.history.domain.model.HistoryCartItem
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

@ProvidedTypeConverter
class HistoryDaoTypeConverters @Inject constructor(
    private val moshi: Moshi
) {

    @TypeConverter
    fun fromHistoryCartItemsToString(
        value: List<HistoryCartItem>,
    ): String {
        val type = Types.newParameterizedType(
            List::class.java,
            HistoryCartItem::class.java
        )
        val jsonAdapter = moshi.adapter<List<HistoryCartItem>>(type)
        return jsonAdapter.toJson(value)
    }

    @TypeConverter
    fun fromStringToHistoryCartItems(value: String): List<HistoryCartItem> {
        val listMyData = Types.newParameterizedType(
            List::class.java,
            HistoryCartItem::class.java
        )
        val jsonAdapter = moshi.adapter<List<HistoryCartItem>>(listMyData)
        return jsonAdapter.fromJson(value) ?: emptyList()
    }
}
