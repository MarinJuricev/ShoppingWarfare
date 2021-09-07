package com.marinj.shoppingwarfare.feature.categorydetail.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.marinj.shoppingwarfare.feature.category.data.model.LocalCategory

data class LocalCategoryProducts(
    @Embedded
    val category: LocalCategory,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryProductId"
    )
    val categoryProductList: List<LocalCategoryProduct>
)
