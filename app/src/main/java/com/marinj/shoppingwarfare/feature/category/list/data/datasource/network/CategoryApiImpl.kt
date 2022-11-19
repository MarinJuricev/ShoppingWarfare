package com.marinj.shoppingwarfare.feature.category.list.data.datasource.network

import com.google.firebase.firestore.FirebaseFirestore
import com.marinj.shoppingwarfare.core.data.JsonConverter
import com.marinj.shoppingwarfare.core.ext.addWarfareSnapshotListener
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.feature.category.list.data.model.RemoteCategoryItem
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

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

    override suspend fun addCategoryItem(
        categoryItem: RemoteCategoryItem
    ) = suspendCancellableCoroutine { continuation ->
        fireStore
            .getCategoryDocument()
            .set(categoryItem)
            .addOnSuccessListener {
                if (continuation.isActive)
                    continuation.resume(Unit.buildRight())
            }
            .addOnFailureListener {
                continuation.resume(Unknown.buildLeft())
            }
    }

    override suspend fun deleteCategoryItem(
        categoryItem: RemoteCategoryItem
    ) = suspendCancellableCoroutine { continuation ->
        fireStore
            .getCategoryDocument()
            .delete()
            .addOnSuccessListener {
                if (continuation.isActive)
                    continuation.resume(Unit.buildRight())
            }
            .addOnFailureListener {
                continuation.resume(Unknown.buildLeft())
            }
    }

    private fun FirebaseFirestore.getCategoryDocument() =
        this.collection(CATEGORY_COLLECTION)
            .document(CATEGORY_DOCUMENT)
}

private const val CATEGORY_COLLECTION = "category"
private const val CATEGORY_DOCUMENT = "categoryDocument"