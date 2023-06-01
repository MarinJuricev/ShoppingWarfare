package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.feature.cart.presentation.model.ReceiptStatus
import javax.inject.Inject

class ValidateReceiptPath @Inject constructor() {

    operator fun invoke(receiptPath: String?): ReceiptStatus = when {
        receiptPath.isNullOrBlank() -> ReceiptStatus.Error
        else -> ReceiptStatus.Taken(receiptPath)
    }
}
