package com.marinj.shoppingwarfare

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareNavigation
import com.marinj.shoppingwarfare.core.navigation.Navigator
import com.marinj.shoppingwarfare.core.theme.ShoppingWarfareTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupShoppingWarfareSplash()
        setContent {
            ShoppingWarfareTheme {
                ShoppingWarfareNavigation(
                    navigator = navigator,
                )
            }
        }
    }
}
