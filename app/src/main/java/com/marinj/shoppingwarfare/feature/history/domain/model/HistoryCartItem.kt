package com.marinj.shoppingwarfare.feature.history.domain.model

data class HistoryCartItem(
    val id: String,
    val categoryName: String,
    val name: String,
    val quantity: Int
)
