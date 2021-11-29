package com.marinj.shoppingwarfare.feature.cart.domain.usecase

import com.marinj.shoppingwarfare.feature.cart.presentation.model.ReceiptStatus
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ValidateReceiptPath @Inject constructor() {

    operator fun invoke(receiptPath: String?): ReceiptStatus =
        if (receiptPath.isNullOrBlank()) {
            ReceiptStatus.Error
        } else {
            ReceiptStatus.Taken(receiptPath)
        }
}
