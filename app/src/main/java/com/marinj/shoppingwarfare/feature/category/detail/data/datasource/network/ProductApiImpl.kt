package com.marinj.shoppingwarfare.feature.category.detail.data.datasource.network

import com.google.firebase.firestore.FirebaseFirestore
import com.marinj.shoppingwarfare.core.data.JsonConverter
import com.marinj.shoppingwarfare.core.ext.addWarfareSnapshotListener
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.core.result.toLeft
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
    ) = callbackFlow<List<RemoteProduct>> {
        val subscription = fireStore
            .getProductDocument(categoryId)
            .addWarfareSnapshotListener(
                onDataSuccess = { documents ->
                    jsonConverter.decode<RemoteProduct>(documents)
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
            .getProductDocument(product.categoryId)
            .set(product)
            .addOnSuccessListener {
                if (continuation.isActive) {
                    continuation.resume(Unit.buildRight())
                }
            }
            .addOnFailureListener { exception: Exception ->
                continuation.resume(exception.toLeft())
            }
    }

    override suspend fun deleteProductById(
        id: String,
    ): Either<Failure, Unit> = suspendCancellableCoroutine { continuation ->
        fireStore
            .getProductDocument(categoryId = id)
            .delete()
            .addOnSuccessListener {
                if (continuation.isActive) {
                    continuation.resume(Unit.buildRight())
                }
            }
            .addOnFailureListener {
                continuation.resume(Failure.Unknown.buildLeft())
            }
    }

    private fun FirebaseFirestore.getProductDocument(
        categoryId: String,
    ) = collection(CATEGORY_COLLECTION)
        .document(categoryId)
}

private const val CATEGORY_COLLECTION = "category"
