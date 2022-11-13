package com.marinj.shoppingwarfare.feature.history.list.data.datasource

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.marinj.shoppingwarfare.core.data.JsonConverter
import com.marinj.shoppingwarfare.feature.history.list.domain.model.HistoryCartItem
import javax.inject.Inject

@ProvidedTypeConverter
class HistoryDaoTypeConverters @Inject constructor(
    private val json: JsonConverter
) {

    @TypeConverter
    fun fromHistoryCartItemsToString(
        value: List<HistoryCartItem>,
    ): String = json.toJson(value)

    @TypeConverter
    fun fromStringToHistoryCartItems(
        value: String
    ) = json.decode<List<HistoryCartItem>>(value)
}
