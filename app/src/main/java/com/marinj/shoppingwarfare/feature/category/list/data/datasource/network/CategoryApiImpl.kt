package com.marinj.shoppingwarfare.feature.category.list.data.datasource.network

import com.google.firebase.firestore.FirebaseFirestore
import com.marinj.shoppingwarfare.core.data.JsonConverter
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import com.marinj.shoppingwarfare.core.result.toLeft
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
            .getCategoryCollection()
            .addSnapshotListener { value, error ->
                val cities = ArrayList<String>()
                for (doc in value!!) {
                    doc.getString("name")?.let {
                        cities.add(it)
                    }
                }
            }
//            .addWarfareSnapshotListener { data ->
//                jsonConverter.decode<RemoteCategoryItem>(data)
//            }

        awaitClose {
            subscription.remove()
        }
    }

    override suspend fun addCategoryItem(
        categoryItem: RemoteCategoryItem
    ) = suspendCancellableCoroutine { continuation ->
        fireStore
            .getCategoryCollection()
            .add(categoryItem)
            .addOnSuccessListener {
                if (continuation.isActive)
                    continuation.resume(Unit.buildRight())
            }
            .addOnFailureListener { exception: Exception ->
                continuation.resume(exception.toLeft())
            }
    }

    override suspend fun deleteCategoryItemById(
        categoryId: String,
    ) = suspendCancellableCoroutine { continuation ->
        fireStore
            .getCategoryCollection()
            .document()
            .delete()
            .addOnSuccessListener {
                if (continuation.isActive)
                    continuation.resume(Unit.buildRight())
            }
            .addOnFailureListener {
                continuation.resume(Unknown.buildLeft())
            }
    }

    private fun FirebaseFirestore.getCategoryCollection() =
        this.collection(CATEGORY_COLLECTION)

}

private const val CATEGORY_COLLECTION = "category"
