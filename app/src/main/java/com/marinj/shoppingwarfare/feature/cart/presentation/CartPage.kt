package com.marinj.shoppingwarfare.feature.cart.presentation

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
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.R.string
import com.marinj.shoppingwarfare.core.components.DottedLine
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareEmptyScreen
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareIconButton
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.ext.openAppSystemSettings
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CartTopBar
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CameraPermissionFlow
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartItemList
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEffect.CartItemDeleted
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEffect.Error
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.OnGetCartItems
import kotlinx.coroutines.flow.collect

@Composable
fun CartPage(
    cartViewModel: CartViewModel = hiltViewModel(),
    setupTopBar: (TopBarEvent) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val viewState by cartViewModel.viewState.collectAsState()
    val context = LocalContext.current
    var showCameraPermission by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(
        key1 = Unit,
        block = {
            cartViewModel.onEvent(OnGetCartItems)
            setupTopBar(CartTopBar())
        }
    )

    LaunchedEffect(key1 = cartViewModel.viewEffect) {
        cartViewModel.viewEffect.collect { cartEffect ->
            when (cartEffect) {
                is CartItemDeleted -> scaffoldState.snackbarHostState.showSnackbar(
                    context.getString(
                        R.string.cart_item_deleted,
                        cartEffect.cartItemName,
                    )
                )
                is Error -> scaffoldState.snackbarHostState.showSnackbar(
                    message = cartEffect.errorMessage
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
    ) {
        Box {
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
                                R.string.empty_cart_message
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
                            Text(text = stringResource(id = R.string.checkout))
                        }
                        ShoppingWarfareIconButton(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            enabled = viewState.cartData.isNotEmpty(),
                            onClick = { showCameraPermission = !showCameraPermission }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.camera_icon),
                                tint = Color.White,
                                contentDescription = stringResource(string.decrease_quantity)
                            )
                        }
                    }
                }
            }
            // TODO: Remove the Box and put this either in a bottom-sheet or in a dialog
            if (showCameraPermission) {
                CameraPermissionFlow {
                    context.openAppSystemSettings()
                }
            }
        }
    }
}
