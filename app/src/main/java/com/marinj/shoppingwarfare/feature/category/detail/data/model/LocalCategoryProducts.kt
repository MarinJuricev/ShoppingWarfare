package com.marinj.shoppingwarfare.feature.category.detail.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.marinj.shoppingwarfare.core.result.takeRightOrNull
import com.marinj.shoppingwarfare.feature.category.list.data.model.LocalCategory

data class LocalCategoryProducts(
    @Embedded
    val category: LocalCategory,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryProductId",
    )
    val productList: List<LocalProduct>,
) {
    fun toProductOrNull () = productList.map {
        it.toDomain().takeRightOrNull()
    }
}
