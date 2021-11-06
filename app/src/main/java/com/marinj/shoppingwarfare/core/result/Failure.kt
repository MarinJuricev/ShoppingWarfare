package com.marinj.shoppingwarfare.core.result

sealed interface Failure {
    data class ErrorMessage(val errorMessage: String) : Failure
    object Unknown : Failure
}
