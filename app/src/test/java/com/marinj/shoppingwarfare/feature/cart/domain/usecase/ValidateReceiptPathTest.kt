package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.feature.cart.presentation.model.ReceiptStatus
import io.kotest.matchers.shouldBe
import org.junit.Test

class ValidateReceiptPathTest {

    private val sut = ValidateReceiptPath()

    @Test
    fun `invoke SHOULD return ReceiptStatusError when receiptPath is null`() {
        val receiptPath = null

        val result = sut(receiptPath = receiptPath)
        val expectedResult = ReceiptStatus.Error

        result shouldBe expectedResult
    }

    @Test
    fun `invoke SHOULD return ReceiptStatusError when receiptPath is empty`() {
        val receiptPath = ""

        val result = sut(receiptPath = receiptPath)
        val expectedResult = ReceiptStatus.Error

        result shouldBe expectedResult
    }

    @Test
    fun `invoke SHOULD return ReceiptStatusError when receiptPath is blank`() {
        val receiptPath = "        "

        val result = sut(receiptPath = receiptPath)
        val expectedResult = ReceiptStatus.Error

        result shouldBe expectedResult
    }

    @Test
    fun `invoke SHOULD return ReceiptStatusTaken when receiptPath is not null or blank`() {
        val receiptPath = "receiptPath"

        val result = sut(receiptPath = receiptPath)
        val expectedResult = ReceiptStatus.Taken(receiptPath)

        result shouldBe expectedResult
    }
}
