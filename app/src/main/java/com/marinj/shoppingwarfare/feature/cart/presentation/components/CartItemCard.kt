import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.feature.cart.domain.model.CartItem
import com.marinj.shoppingwarfare.feature.cart.presentation.components.QuantityPicker
import com.marinj.shoppingwarfare.feature.cart.presentation.model.CartEvent

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onCartEvent: (CartEvent) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            CartDescription(cartItem.name)
            QuantityPicker(
                cartItem = cartItem,
                onCartEvent = onCartEvent,
            )
        }
    }
}

@Composable
private fun CartDescription(cartItemName: String) {
    Column(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.cart_name),
            style = MaterialTheme.typography.body2,
            color = if (MaterialTheme.colors.isLight) Color.LightGray else Color.White
        )
        Text(
            text = cartItemName,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
        )
    }
}
