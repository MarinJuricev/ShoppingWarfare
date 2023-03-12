package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import arrow.core.left
import arrow.core.right
import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import org.junit.Before
import org.junit.Test

class ValidateCartNameTest {

    private lateinit var sut: ValidateCartName

    @Before
    fun setUp() {
        sut = ValidateCartName()
    }

    @Test
    fun `invoke SHOULD return Left when cartName is empty`() {
        val cartName = ""

        val result = sut(cartName)

        assertThat(result).isEqualTo(ErrorMessage("CartName can't be empty").left())
    }

    @Test
    fun `invoke SHOULD return Left when cartName is blank`() {
        val cartName = "        "

        val result = sut(cartName)

        assertThat(result).isEqualTo(ErrorMessage("CartName can't be empty").left())
    }

    @Test
    fun `invoke SHOULD return Right when cartName is empty`() {
        val cartName = "cartName"

        val result = sut(cartName)

        assertThat(result).isEqualTo(Unit.right())
    }
}
