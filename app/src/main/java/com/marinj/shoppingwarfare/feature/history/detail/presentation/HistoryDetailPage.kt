package com.marinj.shoppingwarfare.feature.history.detail.presentation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareEmptyScreen
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryDetailTopBar
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailEvent.OnGetHistoryDetail
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailViewEffect.Error
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailViewState
import com.marinj.shoppingwarfare.feature.history.detail.presentation.viewmodel.HistoryDetailViewModel
import com.marinj.shoppingwarfare.feature.history.list.presentation.model.UiHistoryItem
import kotlinx.coroutines.flow.collect
import java.io.File

@Composable
fun HistoryDetailPage(
    historyItemId: String,
    setupTopBar: (TopBarEvent) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    historyDetailViewModel: HistoryDetailViewModel = hiltViewModel(),
) {
    val viewState by historyDetailViewModel.viewState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        setupTopBar(HistoryDetailTopBar())
        historyDetailViewModel.onEvent(OnGetHistoryDetail(historyItemId))
    }

    LaunchedEffect(key1 = historyDetailViewModel.viewEffect) {
        historyDetailViewModel.viewEffect.collect { historyEffect ->
            when (historyEffect) {
                is Error -> scaffoldState.snackbarHostState.showSnackbar(historyEffect.errorMessage)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
    ) {
        when {
            viewState.isLoading -> ShoppingWarfareLoadingIndicator()
            viewState.uiHistoryItem == UiHistoryItem.DEFAULT ->
                ShoppingWarfareEmptyScreen(stringResource(R.string.history_detail_empty_message))
            else -> HistoryDetailInfo(viewState)
        }
    }
}

@Composable
private fun HistoryDetailInfo(
    viewState: HistoryDetailViewState,
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
            painter = rememberHistoryDetailInfoPainter(context, viewState),
            contentDescription = stringResource(id = R.string.receipt_image_content_description)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(
                    R.string.history_detail_cart_name,
                    viewState.uiHistoryItem.cartName
                ),
                style = MaterialTheme.typography.body1
            )
            Text(
                text = stringResource(
                    R.string.history_detail_date,
                    viewState.uiHistoryItem.date
                ),
                style = MaterialTheme.typography.body1
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(
                items = viewState.uiHistoryItem.historyCartItems,
                key = { it.id }
            ) { historyCartItem ->
                Card {
                    Text(historyCartItem.name)
                }
            }
        }
    }
}

@Composable
private fun rememberHistoryDetailInfoPainter(
    context: Context,
    viewState: HistoryDetailViewState
): Painter {
    return if (viewState.uiHistoryItem?.receiptPath != null) {
        rememberImagePainter(
            data = File(
                context.filesDir,
                viewState.uiHistoryItem.receiptPath,
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
