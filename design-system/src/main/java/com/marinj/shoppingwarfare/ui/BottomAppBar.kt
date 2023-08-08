package com.marinj.shoppingwarfare.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SWNavigationMenu(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        content = content,
    )
}

@Composable
fun RowScope.SWNavigationItem(
    selected: Boolean,
    onClick: () -> Unit,
    label: @Composable (() -> Unit)? = null,
    icon: @Composable () -> Unit,
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        alwaysShowLabel = false,
        label = label,
        icon = icon,
    )
}

@Preview
@Composable
fun SWBottomAppBarPreview() {
    SWNavigationMenu {
        repeat(4) {
            SWNavigationItem(
                selected = it == 2,
                onClick = {},
                label = { TextBodyMedium(text = "Label") },
                icon = { Icon(Icons.Default.Favorite, contentDescription = "") },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SWNavigationItemPreview() {
    Row {
        SWNavigationItem(
            selected = true,
            onClick = {},
            label = { TextBodyMedium(text = "Favorite") },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "") },
        )
        SWNavigationItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Delete, contentDescription = "") },
            label = { TextBodyMedium(text = "Delete") },
        )
    }
}
