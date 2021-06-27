package com.marinj.shoppingwarfare.core.result

sealed class Failure {
    data class ErrorMessage(val errorMessage: String) : Failure()
    object Unknown : Failure()
}
