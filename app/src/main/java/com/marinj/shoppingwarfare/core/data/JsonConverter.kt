package com.marinj.shoppingwarfare.core.data

interface JsonConverter {

    fun <T> fromMapToObject(origin: Map<String, Any>): T
}