package com.marinj.shoppingwarfare.feature.cart.presentation

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R.drawable
import com.marinj.shoppingwarfare.R.string
import com.marinj.shoppingwarfare.core.components.DottedLine
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareEmptyScreen
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareIconButton
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.ext.expandOrCollapse
import com.marinj.shoppingwarfare.core.ext.openAppSystemSettings
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CartTopBar
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartCameraPermission
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartItemList
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEffect.CartItemDeleted
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEffect.Error
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.OnGetCartItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
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
                is CartItemDeleted -> bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    context.getString(
                        string.cart_item_deleted,
                        cartEffect.cartItemName,
                    )
                )
                is Error -> bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                    message = cartEffect.errorMessage
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
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,

            ) {
                DottedLine(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth(),
                    step = 10.dp,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxWidth(0.8f)
                            .padding(16.dp),
                        enabled = viewState.cartData.isNotEmpty(),
                        onClick = { /*TODO*/ }
                    ) {
                        Text(text = stringResource(id = string.checkout))
                    }
                    ShoppingWarfareIconButton(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        enabled = viewState.cartData.isNotEmpty(),
                        onClick = {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.expandOrCollapse()
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = drawable.camera_icon),
                            tint = Color.White,
                            contentDescription = stringResource(string.decrease_quantity)
                        )
                    }
                }
            }
        }
    }
}
