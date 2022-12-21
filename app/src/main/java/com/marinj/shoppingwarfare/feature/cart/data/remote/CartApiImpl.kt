package com.marinj.shoppingwarfare.feature.cart.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.marinj.shoppingwarfare.feature.cart.data.model.RemoteCartItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CartApiImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
) : CartApi {

    override fun observeCartItems(): Flow<List<RemoteCartItem>> = callbackFlow {
        val subscription = fireStore
            .getCartDocument()
            .addSnapshotListener { snapshot, throwable ->
                if (snapshot?.exists() == true) {
                    snapshot.data
//                    val versionCode = snapshot.data().toRemoteCar
//                    trySend(Success(versionCode!!.toInt()))
                }
            }

        awaitClose { subscription.remove() }
    }

    override suspend fun addCartItem(cartItem: RemoteCartItem) {
        fireStore
            .getCartDocument()
            .set(cartItem)
    }

    private fun FirebaseFirestore.getCartDocument() =
        this.collection(CART_COLLECTION)
            .document(CART_DOCUMENT)
}

private const val CART_COLLECTION = "cart"
private const val CART_DOCUMENT = "cartDocument"
