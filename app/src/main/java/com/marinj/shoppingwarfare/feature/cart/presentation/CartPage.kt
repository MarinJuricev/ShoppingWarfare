package com.marinj.shoppingwarfare.feature.cart.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.DottedLine
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareEmptyScreen
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CartTopBar
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
                    .fillMaxHeight(0.2f)
                    .fillMaxWidth()
            ) {
                DottedLine(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth(),
                    step = 10.dp,
                )
            }
        }
    }
}
