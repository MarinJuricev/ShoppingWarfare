package com.marinj.shoppingwarfare.core.data

import com.google.common.truth.Truth.assertThat
import kotlinx.serialization.json.Json
import org.junit.Test

class MoshiJsonConverterTest {

    private val json: Json = Json
    val sut: JsonConverter = JsonConverter(json)

    @Test
    fun `decode SHOULD return valid object`() {
        val origin = mapOf(
            "name" to NAME,
            "age" to AGE
        )

        val result = sut.decode<Test>(origin)
        val expectedResult = Test(
            name = NAME,
            age = AGE,
        )

        assertThat(result).isEqualTo(expectedResult)
    }
}

@kotlinx.serialization.Serializable
data class Test(
    val name: String,
    val age: Int,
)

private const val NAME = "Test"
private const val AGE = 20
