package com.marinj.shoppingwarfare.feature.category.list.data.datasource.network

import arrow.core.left
import arrow.core.right
import com.google.firebase.firestore.FirebaseFirestore
import com.marinj.shoppingwarfare.core.data.JsonConverter
import com.marinj.shoppingwarfare.core.ext.addWarfareSnapshotListener
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.core.result.Failure.Unknown
import com.marinj.shoppingwarfare.feature.category.list.data.model.RemoteCategory
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class CategoryApiImpl @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val jsonConverter: JsonConverter,
) : CategoryApi {

    override fun observeCategories() = callbackFlow {
        val subscription = fireStore
            .getCategoryCollection()
            .addWarfareSnapshotListener(
                onDataSuccess = { collection ->
                    collection.mapNotNull { document ->
                        document.data?.let {
                            jsonConverter.decode<RemoteCategory>(it)
                                ?.copy(categoryId = document.id)
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

    override suspend fun addCategoryItem(
        categoryItem: RemoteCategory,
    ) = suspendCancellableCoroutine { continuation ->
        fireStore
            .getCategoryCollection()
            .document(categoryItem.categoryId)
            .set(categoryItem)
            .addOnCompleteListener { task ->
                when {
                    continuation.isActive && task.isSuccessful -> continuation.resume(Unit.right())
                    else -> continuation.resume(
                        Failure.ErrorMessage("Error while adding category item: ${task.exception?.message}").left(),
                    )
                }
            }
    }

    override suspend fun deleteCategoryItemById(
        categoryId: String,
    ) = suspendCancellableCoroutine { continuation ->
        fireStore
            .getCategoryCollection()
            .document(categoryId)
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

    private fun FirebaseFirestore.getCategoryCollection() =
        collection(CATEGORY_COLLECTION)
}

private const val CATEGORY_COLLECTION = "category"
