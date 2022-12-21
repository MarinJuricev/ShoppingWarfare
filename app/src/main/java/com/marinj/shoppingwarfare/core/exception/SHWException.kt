package com.marinj.shoppingwarfare.core.exception

data class SHWException(val errorMessage: String) : Throwable(errorMessage)
