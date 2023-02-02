package com.marinj.shoppingwarfare.fixtures.category

import com.marinj.shoppingwarfare.core.result.takeRightOrNull
import com.marinj.shoppingwarfare.feature.category.detail.data.model.LocalCategoryProducts
import com.marinj.shoppingwarfare.feature.category.detail.data.model.LocalProduct
import com.marinj.shoppingwarfare.feature.category.detail.data.model.RemoteProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.list.data.model.LocalCategory

fun buildRemoteProduct(
    providedProductId: String = "",
    providedCategoryId: String = "",
    providedCategoryName: String = "",
    providedName: String = "",
) = RemoteProduct(
    productId = providedProductId,
    categoryId = providedCategoryId,
    categoryName = providedCategoryName,
    name = providedName,
)

fun buildLocalProduct(
    providedProductId: String = "",
    providedCategoryId: String = "",
    providedCategoryName: String = "",
    providedName: String = "",
) = LocalProduct(
    productId = providedProductId,
    categoryProductId = providedCategoryId,
    categoryName = providedCategoryName,
    name = providedName,
)

fun buildLocalCategoryProducts(
    providedLocalCategory: LocalCategory = buildLocalCategory(),
    providedLocalProductList: List<LocalProduct> = emptyList(),
) = LocalCategoryProducts(
    category = providedLocalCategory,
    productList = providedLocalProductList,
)

fun buildProduct(
    providedProductId: String = "",
    providedCategoryId: String = "",
    providedCategoryName: String = "",
    providedName: String = PRODUCT_NAME,
) = Product.of(
    id = providedProductId,
    categoryId = providedCategoryId,
    categoryName = providedCategoryName,
    name = providedName,
).takeRightOrNull()!!

//class FakeSuccessProductDao(
//    private val localProductsToReturn: MutableList<LocalProduct> = mutableListOf(),
//) : ProductDao {
//
//    override fun observeProductsForGivenCategoryId(
//        categoryId: String,
//    ): Flow<List<LocalCategoryProducts>> = flow {
//        emit(localProductsToReturn)
//    }
//
//    override suspend fun upsertProduct(entity: LocalProduct): Long {
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun deleteProductById(productId: String) {
//        TODO("Not yet implemented")
//    }
//
//}

private const val PRODUCT_NAME = "productName"