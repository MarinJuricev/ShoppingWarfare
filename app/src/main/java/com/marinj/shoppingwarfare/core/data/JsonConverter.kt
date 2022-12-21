package com.marinj.shoppingwarfare.core.data

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
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

    inline fun <reified T> decode(
        origin: Map<String, Any?>,
    ): T? = tryOrNull {
        origin.toJsonElement().let {
            json.decodeFromJsonElement(it)
        }
    }

    inline fun <reified T> decode(
        origin: List<Any>,
    ): T? = tryOrNull {
        origin.toJsonElement().let {
            json.decodeFromJsonElement(it)
        }
    }

    inline fun <reified T> decode(
        origin: String,
    ): T? = tryOrNull {
        json.decodeFromString(origin)
    }

    inline fun <reified T> tryOrNull(
        action: () -> T,
    ): T? = try {
        action()
    } catch (e: Throwable) {
        null
    }

    inline fun <reified T> toJson(
        origin: List<T>,
    ): String = origin.toJsonElement().let {
        json.encodeToString(it)
    }

    // Needs to be open duo to the nature of inline and reified for fromMapToObject
    fun Collection<*>.toJsonElement(): JsonElement =
        JsonArray(mapNotNull { it.toJsonElement() })

    // Needs to be open duo to the nature of inline and reified for fromMapToObject
    fun Map<*, *>.toJsonElement(): JsonElement = JsonObject(
        mapNotNull {
            (it.key as? String ?: return@mapNotNull null) to it.value.toJsonElement()
        }.toMap(),
    )

    // Needs to be open duo to the nature of inline and reified for fromMapToObject
    fun Any?.toJsonElement(): JsonElement = when (this) {
        null -> JsonNull
        is Map<*, *> -> toJsonElement()
        is Collection<*> -> toJsonElement()
        is Boolean -> JsonPrimitive(this)
        is Number -> JsonPrimitive(this)
        is String -> JsonPrimitive(this)
        is Enum<*> -> JsonPrimitive(this.toString())
        else -> error("Can't serialize unknown collection type: $this")
    }
}
