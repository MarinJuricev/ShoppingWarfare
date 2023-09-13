package com.marinj.shoppingwarfare.feature.category.detail.data.datasource.network

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.google.firebase.firestore.FirebaseFirestore
import com.marinj.shoppingwarfare.core.data.JsonConverter
import com.marinj.shoppingwarfare.core.ext.addWarfareSnapshotListener
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.feature.category.detail.data.model.RemoteProduct
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class ProductApiImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val jsonConverter: JsonConverter,
) : ProductApi {
    override fun observeProductsForGivenCategoryId(
        categoryId: String,
    ) = callbackFlow {
        val subscription = fireStore
            .getProductCollection(categoryId)
            .addWarfareSnapshotListener(
                onDataSuccess = { collection ->
                    collection
                        .mapNotNull { document ->
                            document.data?.let { jsonConverter.decode<RemoteProduct>(it) }
                        }.let(::trySend)
                },
                onError = { throwable ->
                    throw throwable
                },
            )

        awaitClose {
            subscription.remove()
        }
    }

    override suspend fun addProduct(
        product: RemoteProduct,
    ): Either<Failure, Unit> = suspendCancellableCoroutine { continuation ->
        fireStore
            .getProductCollection(product.categoryId)
            .add(product)
            .addOnSuccessListener {
                if (continuation.isActive) {
                    continuation.resume(Unit.right())
                }
            }
            .addOnFailureListener { exception: Exception ->
                continuation.resume(
                    ErrorMessage("Failed to addProduct $product, cause: ${exception.message}").left(),
                )
            }
    }

    override suspend fun deleteProductById(
        id: String,
    ): Either<Failure, Unit> = suspendCancellableCoroutine { continuation ->
        fireStore
            .getProductCollection(categoryId = id)
            .document(id)
            .delete()
            .addOnSuccessListener {
                if (continuation.isActive) {
                    continuation.resume(Unit.right())
                }
            }
            .addOnFailureListener {
                continuation.resume(Unknown.left())
            }
    }

    private fun FirebaseFirestore.getProductCollection(
        categoryId: String,
    ) = collection(CATEGORY_COLLECTION)
        .document(categoryId)
        .collection(PRODUCT_COLLECTION)
}

private const val CATEGORY_COLLECTION = "category"
private const val PRODUCT_COLLECTION = "products"
