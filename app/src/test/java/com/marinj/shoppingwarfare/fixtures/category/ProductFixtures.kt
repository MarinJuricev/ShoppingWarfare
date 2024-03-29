package com.marinj.shoppingwarfare.fixtures.category

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.db.LocalProduct
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.domain.usecase.AddToCart
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.local.ProductDao
import com.marinj.shoppingwarfare.feature.category.detail.data.datasource.network.ProductApi
import com.marinj.shoppingwarfare.feature.category.detail.data.model.RemoteProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.model.Product.Companion.Product
import com.marinj.shoppingwarfare.feature.category.detail.domain.repository.ProductRepository
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.CreateProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.DeleteProduct
import com.marinj.shoppingwarfare.feature.category.detail.domain.usecase.ObserveProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

fun buildRemoteProduct(
    providedProductId: String = "",
    providedCategoryId: String = "",
    providedCategoryName: String = "",
    providedName: String = "",
) = RemoteProduct(
    id = providedProductId,
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
    id = providedProductId,
    categoryId = providedCategoryId,
    categoryName = providedCategoryName,
    name = providedName,
)

fun buildProduct(
    providedProductId: String = PRODUCT_ID,
    providedCategoryName: String = CATEGORY_NAME,
    providedCategoryId: String = CATEGORY_ID,
    providedName: String = PRODUCT_NAME,
) = Product(
    id = providedProductId,
    categoryId = providedCategoryId,
    categoryName = providedCategoryName,
    name = providedName,
).getOrNull()!!

class FakeSuccessProductDao(
    private val localProductsToEmit: List<LocalProduct> = emptyList(),
) : ProductDao {

    val localProducts = mutableListOf<LocalProduct>()

    override fun observeProductsForGivenCategoryId(
        categoryId: String,
    ): Flow<List<LocalProduct>> = flow {
        emit(localProductsToEmit)
    }

    override suspend fun upsertProduct(
        entity: LocalProduct,
    ) {
        localProducts.add(entity)
        return
    }

    override suspend fun deleteProductById(productId: String) {
        localProducts.removeIf { it.id == productId }
    }
}

class FakeSuccessProductApi(
    private val productsToEmit: List<RemoteProduct> = listOf(buildRemoteProduct()),
) : ProductApi {

    val remoteProducts = mutableListOf<RemoteProduct>()
    override fun observeProductsForGivenCategoryId(
        categoryId: String,
    ): Flow<List<RemoteProduct>> = flowOf(productsToEmit)

    override suspend fun addProduct(
        product: RemoteProduct,
    ): Either<Failure, Unit> {
        remoteProducts.add(product)
        return Unit.right()
    }

    override suspend fun deleteProduct(
        product: RemoteProduct,
    ): Either<Failure, Unit> {
        remoteProducts.removeIf { it.id == product.id }
        return Unit.right()
    }
}

object FakeFailureProductApi : ProductApi {

    override fun observeProductsForGivenCategoryId(
        categoryId: String,
    ): Flow<List<RemoteProduct>> = emptyFlow()

    override suspend fun addProduct(
        product: RemoteProduct,
    ): Either<Failure, Unit> = Unknown.left()

    override suspend fun deleteProduct(
        product: RemoteProduct,
    ): Either<Failure, Unit> = Unknown.left()
}

class FakeSuccessProductRepository(
    private val productListToObserve: List<Product> = listOf(buildProduct()),
) : ProductRepository {
    override fun observeProducts(productId: String): Flow<List<Product>> = flow {
        emit(productListToObserve)
    }

    override suspend fun upsertProduct(product: Product): Either<Failure, Unit> =
        Unit.right()

    override suspend fun deleteProduct(product: Product): Either<Failure, Unit> =
        Unit.right()
}

object FakeFailureProductRepository : ProductRepository {
    override fun observeProducts(productId: String): Flow<List<Product>> = flow {
        throw Throwable()
    }

    override suspend fun upsertProduct(product: Product): Either<Failure, Unit> =
        Unknown.left()

    override suspend fun deleteProduct(product: Product): Either<Failure, Unit> =
        Unknown.left()
}

class FakeSuccessObserveProducts(
    private val productListToReturn: List<Product> = listOf(buildProduct()),
) : ObserveProducts {
    override fun invoke(categoryId: String): Flow<List<Product>> = flowOf(productListToReturn)
}

object FakeFailureObserveProducts : ObserveProducts {
    override fun invoke(categoryId: String): Flow<List<Product>> = flow {
        throw Throwable()
    }
}

object FakeSuccessCreateProduct : CreateProduct {
    override suspend fun invoke(
        categoryId: String,
        categoryName: String,
        productName: String?,
    ): Either<Failure, Unit> = Unit.right()
}

object FakeFailureCreateProduct : CreateProduct {
    override suspend fun invoke(
        categoryId: String,
        categoryName: String,
        productName: String?,
    ): Either<Failure, Unit> = Unknown.left()
}

object FakeSuccessDeleteProduct : DeleteProduct {
    override suspend fun invoke(product: Product) = Unit.right()
}

object FakeFailureDeleteProduct : DeleteProduct {
    override suspend fun invoke(product: Product) = Unknown.left()
}

object FakeSuccessAddToCart : AddToCart {
    override suspend fun invoke(cartItem: CartItem): Either<Failure, Unit> = Unit.right()
}

object FakeFailureAddToCart : AddToCart {
    override suspend fun invoke(cartItem: CartItem): Either<Failure, Unit> = Unknown.left()
}

private const val PRODUCT_ID = "productId"
private const val PRODUCT_NAME = "productName"
private const val CATEGORY_ID = "categoryId"
private const val CATEGORY_NAME = "categoryName"
