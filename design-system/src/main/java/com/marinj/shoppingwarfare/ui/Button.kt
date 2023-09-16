package com.marinj.shoppingwarfare.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.elevatedButtonColors
import androidx.compose.material3.ButtonDefaults.filledTonalButtonColors
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.ButtonDefaults.textButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PrimaryOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        colors = outlinedButtonColors(),
        enabled = enabled,
    ) {
        Text(text = text)
    }
}

@Composable
fun PrimaryOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CircleShape,
    content: @Composable RowScope.() -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        colors = outlinedButtonColors(),
        shape = shape,
        enabled = enabled,
        content = content,
    )
}

@Composable
fun SecondaryOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = SolidColor(MaterialTheme.colorScheme.primary),
        ),
        colors = outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary,
        ),
        enabled = enabled,
    ) {
        Text(text = text)
    }
}

@Composable
fun TertiaryOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        border = ButtonDefaults.outlinedButtonBorder.copy(
            brush = SolidColor(MaterialTheme.colorScheme.primary),
        ),
        colors = outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.tertiary,
        ),
        enabled = enabled,
    ) {
        Text(text = text)
    }
}

@Composable
fun PrimaryElevatedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    ElevatedButton(
        modifier = modifier,
        onClick = onClick,
        colors = elevatedButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
        ),
        enabled = enabled,
    ) {
        Text(text = text)
    }
}

@Composable
fun SecondaryElevatedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    ElevatedButton(
        modifier = modifier,
        onClick = onClick,
        colors = elevatedButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary,
        ),
        enabled = enabled,
    ) {
        Text(text = text)
    }
}

@Composable
fun TertiaryElevatedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    ElevatedButton(
        modifier = modifier,
        onClick = onClick,
        colors = elevatedButtonColors(
            contentColor = MaterialTheme.colorScheme.tertiary,
        ),
        enabled = enabled,
    ) {
        Text(text = text)
    }
}

@Composable
fun PrimaryTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
        ),
        enabled = enabled,
    ) {
        Text(text = text)
    }
}

@Composable
fun SecondaryTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary,
        ),
        enabled = enabled,
    ) {
        Text(text = text)
    }
}

@Composable
fun TertiaryTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        colors = textButtonColors(
            contentColor = MaterialTheme.colorScheme.tertiary,
        ),
        enabled = enabled,
    ) {
        Text(text = text)
    }
}

@Composable
fun PrimaryFilledTonalButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    FilledTonalButton(
        modifier = modifier,
        onClick = onClick,
        colors = filledTonalButtonColors(
            contentColor = MaterialTheme.colorScheme.primary,
        ),
        enabled = enabled,
    ) {
        Text(text = text)
    }
}

@Composable
fun SecondaryFilledTonalButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    FilledTonalButton(
        modifier = modifier,
        onClick = onClick,
        colors = filledTonalButtonColors(
            contentColor = MaterialTheme.colorScheme.secondary,
        ),
        enabled = enabled,
    ) {
        Text(text = text)
    }
}

@Composable
fun TertiaryFilledTonalButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    FilledTonalButton(
        modifier = modifier,
        onClick = onClick,
        colors = textButtonColors(
            contentColor = MaterialTheme.colorScheme.tertiary,
        ),
        enabled = enabled,
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryOutlinedButtonPreview() {
    PrimaryOutlinedButton(
        text = "PrimaryOutlinedButton",
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun SecondaryOutlinedButtonPreview() {
    SecondaryOutlinedButton(
        text = "SecondaryOutlinedButton",
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun TertiaryOutlinedButtonPreview() {
    TertiaryOutlinedButton(
        text = "TertiaryOutlinedButton",
        onClick = {},
    )
}

@Preview(
    showBackground = true,
    backgroundColor = BACKGROUND_COLOR,
)
@Composable
fun PrimaryElevatedButtonPreview() {
    PrimaryElevatedButton(
        text = "PrimaryElevatedButton",
        onClick = {},
    )
}

@Preview(
    showBackground = true,
    backgroundColor = BACKGROUND_COLOR,
)
@Composable
fun SecondaryElevatedButtonPreview() {
    SecondaryElevatedButton(
        text = "SecondaryElevatedButton",
        onClick = {},
    )
}

@Preview(
    showBackground = true,
    backgroundColor = BACKGROUND_COLOR,
)
@Composable
fun TertiaryElevatedButtonPreview() {
    TertiaryElevatedButton(
        text = "TertiaryElevatedButton",
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun PrimaryTextButtonPreview() {
    PrimaryTextButton(
        text = "PrimaryTextButton",
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun SecondaryTextButtonPreview() {
    SecondaryTextButton(
        text = "SecondaryTextButton",
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun TertiaryTextButtonPreview() {
    TertiaryTextButton(
        text = "TertiaryTextButton",
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun PrimaryFilledTonalButtonPreview() {
    PrimaryFilledTonalButton(
        text = "PrimaryFilledTonalButton",
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun SecondaryFilledTonalButtonPreview() {
    SecondaryFilledTonalButton(
        text = "SecondaryFilledTonalButton",
        onClick = {},
    )
}

@Preview(showBackground = true)
@Composable
fun TertiaryFilledTonalButtonPreview() {
    TertiaryFilledTonalButton(
        text = "TertiaryFilledTonalButton",
        onClick = {},
    )
}

private const val BACKGROUND_COLOR = 0xFF7c4dff
