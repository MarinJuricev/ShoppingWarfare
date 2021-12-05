package com.marinj.shoppingwarfare.feature.history.detail.presentation.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.UiHistoryItem
import java.io.File

@Composable
fun HistoryDetailInfo(
    uiHistoryItem: UiHistoryItem,
    context: Context = LocalContext.current,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.fillMaxSize(0.5f),
            painter = rememberHistoryDetailInfoPainter(context, uiHistoryItem),
            contentDescription = stringResource(id = R.string.receipt_image_content_description)
        )
        HistoryDetailCartInfo(uiHistoryItem)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(
                items = uiHistoryItem.historyCartItems,
                key = { it.id },
            ) { historyCartItem ->
                HistoryDetailCard(historyCartItem = historyCartItem)
            }
        }
    }
}

@Composable
private fun rememberHistoryDetailInfoPainter(
    context: Context,
    uiHistoryItem: UiHistoryItem,
): Painter {
    return if (uiHistoryItem.receiptPath != null) {
        rememberImagePainter(
            data = File(
                context.filesDir,
                uiHistoryItem.receiptPath,
            ),
            builder = {
                crossfade(true)
            }
        )
    } else {
        painterResource(
            id = R.drawable.error_icon,
        )
    }
}
