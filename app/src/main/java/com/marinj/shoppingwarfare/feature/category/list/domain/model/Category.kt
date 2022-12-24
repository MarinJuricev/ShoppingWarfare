package com.marinj.shoppingwarfare.feature.category.list.domain.model

data class Category private constructor(
    val id: String,
    val title: String,
    val backgroundColor: Int,
    val titleColor: Int,
) {
    companion object {
        fun of(
            id: String,
            title: String?,
            backgroundColor: Int?,
            titleColor: Int?,
        ): Category? = when {
            title.isNullOrEmpty() -> null
            backgroundColor == null -> null
            titleColor == null -> null
            else -> Category(
                id,
                title,
                backgroundColor,
                titleColor,
            )
        }
    }
}
