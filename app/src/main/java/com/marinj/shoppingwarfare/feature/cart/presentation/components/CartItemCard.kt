import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareSwipeToDismiss
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.components.CartDescription
import com.marinj.shoppingwarfare.feature.cart.presentation.components.QuantityPicker
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent.DeleteCartItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CartItemCard(
    cartItem: CartItem,
    onCartEvent: (CartEvent) -> Unit,
) {
    ShoppingWarfareSwipeToDismiss(
        modifier = Modifier.fillMaxWidth(),
        onDismiss = {},
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(16.dp),
        ) {
            val (cartDescription, quantityPicker, swipeToDismissIcon) = createRefs()
            CartDescription(
                modifier = Modifier
                    .constrainAs(cartDescription) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },
                cartItemName = cartItem.name
            )
            QuantityPicker(
                modifier = Modifier.constrainAs(quantityPicker) {
                    top.linkTo(parent.top)
                    end.linkTo(swipeToDismissIcon.start, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                },
                cartItem = cartItem,
                onCartEvent = onCartEvent,
            )
            Icon(
                modifier = Modifier
                    .constrainAs(swipeToDismissIcon) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable {
                        onCartEvent(DeleteCartItem(cartItem.id))
                    }
                    .size(24.dp),
                imageVector = Icons.Default.KeyboardArrowLeft,
                tint = if (MaterialTheme.colors.isLight) Color.DarkGray else Color.White,
                contentDescription = stringResource(
                    R.string.delete_item,
                    cartItem.name
                )
            )
        }
    }
}