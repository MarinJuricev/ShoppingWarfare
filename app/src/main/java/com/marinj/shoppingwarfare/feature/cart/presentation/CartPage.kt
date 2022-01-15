package com.marinj.shoppingwarfare.feature.cart.presentation

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R.string
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareEmptyScreen
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.ext.expandOrCollapse
import com.marinj.shoppingwarfare.core.ext.openAppSystemSettings
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CartTopBar
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartCameraPermission
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartCheckoutInfo
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartItemList
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.OnGetCartItems
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.CartViewItemDeleted
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.Error
import com.marinj.shoppingwarfare.feature.cart.presentation.viewmodel.CartViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun CartPage(
    cartViewModel: CartViewModel = hiltViewModel(),
    setupTopBar: (TopBarEvent) -> Unit,
    bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,
) {
    val viewState by cartViewModel.viewState.collectAsState()

    LaunchedEffect(Unit) {
        cartViewModel.onEvent(OnGetCartItems)
        setupTopBar(CartTopBar())
    }

    LaunchedEffect(key1 = cartViewModel.viewEffect) {
        cartViewModel.viewEffect.collect { cartEffect ->
            when (cartEffect) {
                is CartViewItemDeleted -> bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    context.getString(
                        string.cart_item_deleted,
                        cartEffect.cartItemName,
                    )
                )
                is Error -> bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    message = cartEffect.errorMessage
                )
                CartViewEffect.CartViewCheckoutCompleted -> bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    context.getString(string.cart_success_message)
                )
            }
        }
    }

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            CartCameraPermission(
                onCartEvent = { cartEvent ->
                    coroutineScope.launch {
                        bottomSheetScaffoldState.expandOrCollapse()
                    }
                    cartViewModel.onEvent(cartEvent)
                },
            ) {
                context.openAppSystemSettings()
            }
        },
        sheetPeekHeight = 0.dp,
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .fillMaxWidth()
            ) {
                when {
                    viewState.isLoading -> ShoppingWarfareLoadingIndicator()
                    viewState.cartData.isEmpty() -> ShoppingWarfareEmptyScreen(
                        message = stringResource(
                            string.empty_cart_message
                        )
                    )
                    viewState.cartData.isNotEmpty() -> CartItemList(
                        cartData = viewState.cartData,
                        onCartEvent = cartViewModel::onEvent,
                    )
                }
            }
            CartCheckoutInfo(
                viewState = viewState,
                onReceiptClick = {
                    coroutineScope.launch {
                        bottomSheetScaffoldState.expandOrCollapse()
                    }
                },
                onCartEvent = cartViewModel::onEvent,
            )
        }
    }
}
