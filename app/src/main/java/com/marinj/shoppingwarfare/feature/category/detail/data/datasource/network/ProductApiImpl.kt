package com.marinj.shoppingwarfare.feature.category.detail.data.datasource.network

import com.google.firebase.firestore.FirebaseFirestore
import com.marinj.shoppingwarfare.core.data.JsonConverter
import com.marinj.shoppingwarfare.core.ext.addWarfareSnapshotListener
import com.marinj.shoppingwarfare.core.result.Either
import com.marinj.shoppingwarfare.core.result.Failure
import com.marinj.shoppingwarfare.feature.category.detail.data.model.RemoteProduct
import com.marinj.shoppingwarfare.feature.category.list.data.model.RemoteCategory
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

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
                    documents.mapNotNull { document ->
//                        document.data?.let {
//                            jsonConverter.decode<RemoteCategory>(it)
//                                ?.copy(categoryId = document.id)
//                        }
                    }
                },
                onError = { throwable ->
                    throw throwable
                }
            )

        awaitClose {
            subscription.remove()
        }
    }

    override suspend fun addProduct(product: RemoteProduct): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProductById(id: String): Either<Failure, Unit> {
        TODO("Not yet implemented")
    }

    private fun FirebaseFirestore.getProductDocument(
        categoryId: String,
    ) = collection(CATEGORY_COLLECTION)
        .document(categoryId)

}

private const val CATEGORY_COLLECTION = "category"