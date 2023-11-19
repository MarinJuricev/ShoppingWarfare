package com.marinj.shoppingwarfare.feature.cart.presentation

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.ext.expandOrCollapse
import com.marinj.shoppingwarfare.core.ext.openAppSystemSettings
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CartTopBar
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartCameraPermission
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartCheckoutInfo
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartItemList
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartTabs
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartListEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.CartViewItemDeleted
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartViewEffect.Error
import com.marinj.shoppingwarfare.feature.cart.presentation.viewmodel.CartViewModel
import com.marinj.shoppingwarfare.ui.ShoppingWarfareEmptyScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    cartViewModel: CartViewModel = hiltViewModel(),
    setupTopBar: (TopBarEvent) -> Unit,
    bottomSheetScaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    context: Context = LocalContext.current,
) {
    LaunchedEffect(Unit) {
        cartViewModel.onEvent(CartListEvent.OnGetCartItems)
        setupTopBar(CartTopBar())
    }

    LaunchedEffect(key1 = cartViewModel.viewEffect) {
        cartViewModel.viewEffect.collect { cartEffect ->
            when (cartEffect) {
                is CartViewItemDeleted -> bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    context.getString(
                        string.cart_item_deleted,
                        cartEffect.cartItemName,
                    ),
                )

                is Error -> bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    message = cartEffect.errorMessage,
                )

                CartViewEffect.CartViewCheckoutCompleted -> bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    context.getString(string.cart_success_message),
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
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            CartTabs(
                selectedTabPosition = viewState.selectedTabPosition,
                cartTabs = viewState.cartTabs,
                onCartEvent = cartViewModel::onEvent,
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .fillMaxWidth(),
            ) {
                when {
                    viewState.uiCartItems.isEmpty() -> ShoppingWarfareEmptyScreen(
                        message = stringResource(
                            string.empty_cart_message,
                        ),
                    )
                    else -> CartItemList(
                        uiCartItems = viewState.uiCartItems,
                        onCartEvent = cartViewModel::onEvent,
                    )
                }

                if (viewState.isLoading) {
                    ShoppingWarfareLoadingIndicator()
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
