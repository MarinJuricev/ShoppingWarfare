package com.marinj.shoppingwarfare.core.data

import com.squareup.moshi.Moshi
import javax.inject.Inject

class MoshiJsonConverter @Inject constructor(
    val moshi: Moshi,
): JsonConverter {
    override fun <T> fromMapToObject(origin: Map<String, Any>): T {
        moshi.
    }
}