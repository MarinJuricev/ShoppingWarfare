package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.marinj.shoppingwarfare.feature.cart.presentation.model.ReceiptStatus
import org.junit.Test

class ValidateReceiptPathTest {

    private val sut = ValidateReceiptPath()

    @Test
    fun `invoke SHOULD return ReceiptStatusError when receiptPath is null`() {
        val receiptPath = null

        val result = sut(receiptPath = receiptPath)
        val expectedResult = ReceiptStatus.Error

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke SHOULD return ReceiptStatusError when receiptPath is empty`() {
        val receiptPath = ""

        val result = sut(receiptPath = receiptPath)
        val expectedResult = ReceiptStatus.Error

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke SHOULD return ReceiptStatusError when receiptPath is blank`() {
        val receiptPath = "        "

        val result = sut(receiptPath = receiptPath)
        val expectedResult = ReceiptStatus.Error

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `invoke SHOULD return ReceiptStatusTaken when receiptPath is not null or blank`() {
        val receiptPath = "receiptPath"

        val result = sut(receiptPath = receiptPath)
        val expectedResult = ReceiptStatus.Taken(receiptPath)

        assertThat(result).isEqualTo(expectedResult)
    }
}
