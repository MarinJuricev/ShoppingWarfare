package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.core.result.Failure.ErrorMessage
import com.marinj.shoppingwarfare.core.result.buildLeft
import com.marinj.shoppingwarfare.core.result.buildRight
import org.junit.Before
import org.junit.Test

class ValidateCartNameTest {

    private lateinit var sut: ValidateCartName

    @Before
    fun setUp() {
        sut = ValidateCartName()
    }

    @Test
    fun `invoke should return Left when cartName is empty`() {
        val cartName = ""

        val result = sut(cartName)

        assertThat(result).isEqualTo(ErrorMessage("CartName can't be empty").buildLeft())
    }

    @Test
    fun `invoke should return Left when cartName is blank`() {
        val cartName = "        "

        val result = sut(cartName)

        assertThat(result).isEqualTo(ErrorMessage("CartName can't be empty").buildLeft())
    }

    @Test
    fun `invoke should return Right when cartName is empty`() {
        val cartName = "cartName"

        val result = sut(cartName)

        assertThat(result).isEqualTo(Unit.buildRight())
    }
}
