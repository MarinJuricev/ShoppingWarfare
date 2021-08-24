package com.marinj.shoppingwarfare

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareNavigation
import com.marinj.shoppingwarfare.core.theme.ShoppingWarfareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupShoppingWarfareSplash()
        setContent {
            ShoppingWarfareTheme {
                ShoppingWarfareNavigation()
            }
        }
    }
}
