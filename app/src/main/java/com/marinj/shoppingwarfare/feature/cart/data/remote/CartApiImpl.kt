package com.marinj.shoppingwarfare.feature.cart.data.remote

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.marinj.shoppingwarfare.core.data.JsonConverter
import com.marinj.shoppingwarfare.core.ext.addWarfareSnapshotListener
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.feature.cart.data.model.RemoteCartItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class CartApiImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val jsonConverter: JsonConverter,
) : CartApi {

    override fun observeCartItems(): Flow<List<RemoteCartItem>> = callbackFlow {
        val subscription = fireStore
            .getCartCollection()
            .addWarfareSnapshotListener(
                onDataSuccess = { collection ->
                    collection.mapNotNull { document ->
                        document.data?.let {
                            jsonConverter.decode<RemoteCartItem>(it)
                                ?.copy(cartItemId = document.id)
                        }
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

    override suspend fun updateCartItemQuantity(
        cartItemId: String,
        newQuantity: Int,
    ): Either<Failure, Unit> = suspendCancellableCoroutine { continuation ->
        fireStore
            .getCartCollection()
            .document(cartItemId)
            .update(CART_QUANTITY, newQuantity)
            .addOnSuccessListener {
                if (continuation.isActive) {
                    continuation.resume(Unit.right())
                }
            }
            .addOnFailureListener { exception: Exception ->
                continuation.resume(
                    ErrorMessage("Error while updating cart item quantity: ${exception.message}").left(),
                )
            }
    }

    override suspend fun updateCartItemIsInBasket(
        cartItemId: String,
        updatedIsInBasket: Boolean,
    ): Either<Failure, Unit> = suspendCancellableCoroutine { continuation ->
        fireStore
            .getCartCollection()
            .document(cartItemId)
            .update(CART_IS_IN_BASKET, updatedIsInBasket)
            .addOnSuccessListener {
                if (continuation.isActive) {
                    continuation.resume(Unit.right())
                }
            }
            .addOnFailureListener { exception: Exception ->
                continuation.resume(
                    ErrorMessage("Error while updating cart item isInBasket: ${exception.message}").left(),
                )
            }
    }

    override suspend fun addCartItem(cartItem: RemoteCartItem): Either<Failure, Unit> =
        suspendCancellableCoroutine { continuation ->
            fireStore
                .getCartCollection()
                .document(cartItem.cartItemId)
                .set(cartItem)
                .addOnSuccessListener {
                    if (continuation.isActive) {
                        continuation.resume(Unit.right())
                    }
                }
                .addOnFailureListener { exception: Exception ->
                    continuation.resume(
                        ErrorMessage("Error while adding category item: ${exception.message}").left(),
                    )
                }
        }

    override suspend fun deleteCartItemById(
        id: String,
    ) = suspendCancellableCoroutine { continuation ->
        fireStore
            .getCartCollection()
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

    override suspend fun deleteCart(): Either<Failure, Unit> =
        suspendCancellableCoroutine { continuation ->
            fireStore
                .getCartCollection()
                .deleteAllDocuments(BATCH_SIZE)
                .fold(
                    ifLeft = { continuation.resume(it.left()) },
                    ifRight = { continuation.resume(Unit.right()) },
                )
        }

    private fun FirebaseFirestore.getCartCollection() =
        collection(CART_COLLECTION)

    private fun CollectionReference.deleteAllDocuments(
        batchSize: Long,
    ): Either<Failure, Task<QuerySnapshot>> = Either.catch {
        this
            .limit(batchSize)
            .get()
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) Unknown.left()

                task.result.documents.forEach {
                    it.reference.delete()
                }

                if (task.result.size() >= batchSize) {
                    deleteAllDocuments(batchSize)
                }
            }
    }.mapLeft { throwable ->
        throwable.message?.let(::ErrorMessage) ?: Unknown
    }
}

private const val CART_COLLECTION = "cart"
private const val CART_QUANTITY = "quantity"
private const val CART_IS_IN_BASKET = "isInBasket"
private const val BATCH_SIZE = 20L
