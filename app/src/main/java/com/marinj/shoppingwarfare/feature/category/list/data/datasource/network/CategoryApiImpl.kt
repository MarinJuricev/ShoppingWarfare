package com.marinj.shoppingwarfare.feature.category.list.data.datasource.network

import com.google.firebase.firestore.FirebaseFirestore
import com.marinj.shoppingwarfare.core.data.JsonConverter
import com.marinj.shoppingwarfare.core.exception.SHWException
import com.marinj.shoppingwarfare.feature.cart.data.model.RemoteCartItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CategoryApiImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val jsonConverter: JsonConverter,
) : CategoryApi {

    override fun observeCartItems(): Flow<List<RemoteCartItem>> = callbackFlow {
        val subscription = fireStore
            .getCategoryDocument()
            .addSnapshotListener { value, error ->
                if(error != null)
                    throw SHWException(error.message ?: "Unknown Error Occured")
            }
    }

    override suspend fun addCartItem(cartItem: RemoteCartItem) {
        TODO("Not yet implemented")
    }

    private fun FirebaseFirestore.getCategoryDocument() =
        this.collection(CATEGORY_COLLECTION)
            .document(CATEGORY_DOCUMENT)
}

private const val CATEGORY_COLLECTION = "category"
private const val CATEGORY_DOCUMENT = "categoryDocument"