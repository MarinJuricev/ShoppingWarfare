package com.marinj.shoppingwarfare.core.ext

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.marinj.shoppingwarfare.core.exception.SHWException

fun DocumentReference.addWarfareSnapshotListener(
    onDataSuccess: (Map<String, Any?>) -> Unit,
    onError: (Throwable) -> Unit,
) = addSnapshotListener { value, error ->
    if (error != null)
        onError(SHWException(error.message ?: "Unknown Error Occurred"))

    val data = value?.data

    data?.let(onDataSuccess) ?: onError(SHWException("No data present"))
}

fun CollectionReference.addWarfareSnapshotListener(
    onDataSuccess: (List<DocumentSnapshot>) -> Unit,
    onError: (Throwable) -> Unit,
) = addSnapshotListener { value, error ->
    if (error != null)
        onError(SHWException(error.message ?: "Unknown Error Occurred"))

    val data = value?.documents

    data?.let(onDataSuccess) ?: onError(SHWException("No data present"))
}
