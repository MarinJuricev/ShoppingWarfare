package com.marinj.shoppingwarfare.feature.cart.presentation.mapper

import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartTab
import javax.inject.Inject

class UiCartTabItemsMapper @Inject constructor() {

    fun map(): List<CartTab> = listOf(
        CartTab(0, R.string.pending_tab),
        CartTab(1, R.string.approved_tab),
    )
}