package com.marinj.shoppingwarfare.feature.cart.presentation.model

import com.marinj.shoppingwarfare.R

data class CartViewState(
    val isLoading: Boolean = true,
    val receiptStatus: ReceiptStatus = ReceiptStatus.Empty,
    val cartName: String = "",
    val isPremiumUser: Boolean = false,
    val selectedTabPosition: Int = 0,
    val cartTabs: List<CartTab> = listOf(
        CartTab(0, R.string.pending_tab),
        CartTab(1, R.string.approved_tab),
    ),
    val uiCartItems: List<UiCartItem> = emptyList(),
)
