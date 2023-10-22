package com.marinj.shoppingwarfare.core.data

import io.kotest.matchers.shouldBe
import kotlinx.serialization.json.Json
import org.junit.Test

class MoshiJsonConverterTest {

    private val json: Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
    }
    val sut = JsonConverter(json)

    @Test
    fun `decode with map origin SHOULD return valid object WHEN the origin contract matches the provided type`() {
        val origin = mapOf(
            "name" to NAME,
            "age" to AGE,
        )

        val result = sut.decode<JsonConverterTest>(origin)
        val expectedResult = JsonConverterTest(
            name = NAME,
            age = AGE,
        )

        result shouldBe expectedResult
    }

    @Test
    fun `decode with map origin SHOULD return null WHEN the origin contract does no match the provided type`() {
        val origin = mapOf(
            "name" to NAME,
            "age" to NAME,
        )

        val result = sut.decode<JsonConverterTest>(origin)

        result shouldBe null
    }

    @Test
    fun `decode with list origin SHOULD return valid object WHEN the origin contract matches the provided type`() {
        val origin = listOf(
            mapOf(
                "name" to NAME,
                "age" to AGE,
            ),
        )

        val result = sut.decode<List<JsonConverterTest>>(origin)
        val expectedResult = JsonConverterTest(
            name = NAME,
            age = AGE,
        )

        result shouldBe listOf(expectedResult)
    }

    @Test
    fun `decode with list origin SHOULD return null WHEN the origin contract does no match the provided type`() {
        val origin = listOf(
            mapOf(
                "name" to NAME,
                "age" to NAME,
            ),
        )

        val result = sut.decode<List<JsonConverterTest>>(origin)

        result shouldBe null
    }

    @Test
    fun `decode with String origin SHOULD return valid object WHEN the origin contract matches the provided type`() {
        val origin = """
            {
               "name":"$NAME",
               "age":"$AGE"
            }
        """.trimIndent()

        val result = sut.decode<JsonConverterTest>(origin)
        val expectedResult = JsonConverterTest(
            name = NAME,
            age = AGE,
        )

        result shouldBe expectedResult
    }

    @Test
    fun `decode with String origin SHOULD return null WHEN the origin does no match the provided type`() {
        val origin = listOf(
            mapOf(
                "name" to NAME,
                "age" to NAME,
            ),
        )

        val result = sut.decode<List<JsonConverterTest>>(origin)

        result shouldBe null
    }

    @Test
    fun `decode with String origin SHOULD return null WHEN the origin is not a valid json`() {
        val origin = "totallyFalse"

        val result = sut.decode<List<JsonConverterTest>>(origin)

        result shouldBe null
    }
}

@kotlinx.serialization.Serializable
data class JsonConverterTest(
    val name: String,
    val age: Int,
)

private const val NAME = "Test"
private const val AGE = 20
