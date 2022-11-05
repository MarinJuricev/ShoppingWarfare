package com.marinj.shoppingwarfare.core.data

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement
import javax.inject.Inject
import javax.inject.Singleton

// TODO: Can't wrap it into a interface since we need to rely on reified T to correctly pass
//       the type to decodeFromJsonElement
@Singleton
class JsonConverter @Inject constructor(
    val json: Json,
) {

    inline fun <reified T> fromMapToObject(
        origin: Map<String, Any>,
    ): T = origin.toJsonElement().let {
        json.decodeFromJsonElement(it)
    }

    private fun Collection<*>.toJsonElement(): JsonElement =
        JsonArray(mapNotNull { it.toJsonElement() })

    fun Map<*, *>.toJsonElement(): JsonElement = JsonObject(
        mapNotNull {
            (it.key as? String ?: return@mapNotNull null) to it.value.toJsonElement()
        }.toMap(),
    )

    private fun Any?.toJsonElement(): JsonElement = when (this) {
        null -> JsonNull
        is Map<*, *> -> toJsonElement()
        is Collection<*> -> toJsonElement()
        else -> JsonPrimitive(toString())
    }
}
