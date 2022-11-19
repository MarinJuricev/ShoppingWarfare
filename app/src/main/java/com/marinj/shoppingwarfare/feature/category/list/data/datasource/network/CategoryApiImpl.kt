package com.marinj.shoppingwarfare.feature.category.list.data.datasource.network

import com.google.firebase.firestore.FirebaseFirestore
import com.marinj.shoppingwarfare.core.data.JsonConverter
import com.marinj.shoppingwarfare.core.ext.addWarfareSnapshotListener
import com.marinj.shoppingwarfare.feature.category.list.data.model.RemoteCategoryItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class CategoryApiImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val jsonConverter: JsonConverter,
) : CategoryApi {

    override fun observeCategoryItems(): Flow<List<RemoteCategoryItem>> = callbackFlow {
        val subscription = fireStore
            .getCategoryDocument()
            .addWarfareSnapshotListener { data ->
                jsonConverter.decode<RemoteCategoryItem>(data)
            }

        awaitClose {
            subscription.remove()
        }
    }

    override suspend fun addCategoryItem(cartItem: RemoteCategoryItem) {
        TODO("Not yet implemented")
    }

    private fun FirebaseFirestore.getCategoryDocument() =
        this.collection(CATEGORY_COLLECTION)
            .document(CATEGORY_DOCUMENT)
}

private const val CATEGORY_COLLECTION = "category"
private const val CATEGORY_DOCUMENT = "categoryDocument"