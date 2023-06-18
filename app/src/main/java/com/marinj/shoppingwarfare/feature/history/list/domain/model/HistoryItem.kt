package com.marinj.shoppingwarfare.feature.history.list.domain.model

import arrow.core.raise.either
import com.marinj.shoppingwarfare.core.model.NonEmptyString
import com.marinj.shoppingwarfare.core.model.NonEmptyString.Companion.NonEmptyString

data class HistoryItem private constructor(
    val id: NonEmptyString,
    val receiptPath: NonEmptyString?,
    val timestamp: Long,
    val cartName: NonEmptyString,
    val historyCartItems: List<HistoryCartItem>,
) {
    companion object {
        fun HistoryItem(
            id: String,
            receiptPath: String?,
            timestamp: Long,
            cartName: String,
            historyCartItems: List<HistoryCartItem>,
        ) = either {
            val mappedId = NonEmptyString(valueToValidate = id, tag = "id").bind()
            val mappedReceiptPath = NonEmptyString(valueToValidate = receiptPath, tag = "receiptPath").bind()
            val mappedCartName = NonEmptyString(valueToValidate = cartName, tag = "cartName").bind()

            HistoryItem(
                id = mappedId,
                receiptPath = mappedReceiptPath,
                timestamp = timestamp,
                cartName = mappedCartName,
                historyCartItems = historyCartItems,
            )
        }
    }
}
