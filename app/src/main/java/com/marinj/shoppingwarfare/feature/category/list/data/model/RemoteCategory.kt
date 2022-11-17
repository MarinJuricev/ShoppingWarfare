package com.marinj.shoppingwarfare.feature.category.list.data.model

import androidx.annotation.Keep

@Keep
@kotlinx.serialization.Serializable
data class RemoteCategory(
    val categoryId: String,
    val title: String,
    val backgroundColor: Int,
    val titleColor: Int,
)
