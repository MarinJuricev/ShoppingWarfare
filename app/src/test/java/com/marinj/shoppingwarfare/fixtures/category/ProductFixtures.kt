package com.marinj.shoppingwarfare.fixtures.category

import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.core.result.takeRightOrNull
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.local.ProductDao
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.network.ProductApi
import com.marinj.shoppingwarfare.feature.category.detail.data.model.LocalCategoryProducts
import com.marinj.shoppingwarfare.feature.category.detail.data.model.LocalProduct
import com.marinj.shoppingwarfare.feature.category.detail.data.model.RemoteProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import com.marinj.shoppingwarfare.feature.category.list.data.model.LocalCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow

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
    providedCategoryName: String = CATEGORY_NAME,
    providedCategoryId: String = CATEGORY_ID,
    providedName: String = PRODUCT_NAME,
) = Product.of(
    id = providedProductId,
    categoryId = providedCategoryId,
    categoryName = providedCategoryName,
    name = providedName,
).takeRightOrNull()!!

class FakeSuccessProductDao(
    private val localProductsToEmit: List<LocalCategoryProducts> = emptyList(),
) : ProductDao {

    val localProducts = mutableListOf<LocalProduct>()

    override fun observeProductsForGivenCategoryId(
        categoryId: String,
    ): Flow<List<LocalCategoryProducts>> = flow {
        emit(localProductsToEmit)
    }

    override suspend fun upsertProduct(
        entity: LocalProduct,
    ): Long {
        localProducts.add(entity)
        return 1L
    }

    override suspend fun deleteProductById(productId: String) {
        localProducts.removeIf { it.productId == productId }
    }
}

class FakeSuccessProductApi(
    private val productsToEmit: List<RemoteProduct> = listOf(buildRemoteProduct()),
) : ProductApi {

    val remoteProducts = mutableListOf<RemoteProduct>()
    override fun observeProductsForGivenCategoryId(
        categoryId: String,
    ): Flow<List<RemoteProduct>> = flow {
        emit(productsToEmit)
    }

    override suspend fun addProduct(
        product: RemoteProduct,
    ): Either<Failure, Unit> {
        remoteProducts.add(product)
        return Unit.buildRight()
    }

    override suspend fun deleteProductById(
        id: String,
    ): Either<Failure, Unit> {
        remoteProducts.removeIf { it.productId == id }
        return Unit.buildRight()
    }
}

object FakeFailureProductApi : ProductApi {

    override fun observeProductsForGivenCategoryId(
        categoryId: String,
    ): Flow<List<RemoteProduct>> = emptyFlow()

    override suspend fun addProduct(
        product: RemoteProduct,
    ): Either<Failure, Unit> = Unknown.buildLeft()

    override suspend fun deleteProductById(
        id: String,
    ): Either<Failure, Unit> = Unknown.buildLeft()
}

class FakeSuccessProductRepository(
    private val productListToObserve: List<Product> = listOf(buildProduct()),
) : ProductRepository {
    override fun observeProducts(productId: String): Flow<List<Product>> = flow {
        emit(productListToObserve)
    }

    override suspend fun upsertProduct(product: Product): Either<Failure, Unit> =
        Unit.buildRight()

    override suspend fun deleteProductById(productId: String): Either<Failure, Unit> =
        Unit.buildRight()
}

object FakeFailureProductRepository : ProductRepository {
    override fun observeProducts(productId: String): Flow<List<Product>> = flow {
        throw Throwable()
    }

    override suspend fun upsertProduct(product: Product): Either<Failure, Unit> =
        Unknown.buildLeft()

    override suspend fun deleteProductById(productId: String): Either<Failure, Unit> =
        Unknown.buildLeft()
}


private const val PRODUCT_NAME = "productName"
private const val CATEGORY_ID = "categoryId"
private const val CATEGORY_NAME = "categoryName"