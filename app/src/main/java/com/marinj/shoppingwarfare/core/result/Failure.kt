package com.marinj.shoppingwarfare.core.result

import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.Failure.Unknown

sealed interface Failure {
    data class ErrorMessage(val errorMessage: String) : Failure
    object Unknown : Failure
}

fun Failure.foldToString(): String = when (this) {
    is ErrorMessage -> errorMessage
    // TODO: 2021-07-18 For now this is good enough, later on expand with Context receiver
    //  so that we can get the string from the resources
    is Unknown -> "Unknown error"
}
