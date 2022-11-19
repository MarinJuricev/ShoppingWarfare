package com.marinj.shoppingwarfare.core.ext

import com.google.firebase.firestore.DocumentReference
import com.marinj.shoppingwarfare.core.exception.SHWException

fun DocumentReference.addWarfareSnapshotListener(
    onDataSuccess: (Map<String, Any?>) -> Unit
) = addSnapshotListener { value, error ->
    if (error != null)
        throw SHWException(error.message ?: "Unknown Error Occurred")

    val data = value?.data ?: throw SHWException("No data present")

    onDataSuccess(data)
}
