package com.marinj.shoppingwarfare.feature.history.detail.presentation

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareEmptyScreen
import com.marinj.shoppingwarfare.core.components.ShoppingWarfareLoadingIndicator
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.HistoryDetailTopBar
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailEvent.OnGetHistoryDetail
import com.marinj.shoppingwarfare.feature.history.detail.presentation.model.HistoryDetailViewEffect.Error
import com.marinj.shoppingwarfare.feature.history.detail.presentation.viewmodel.HistoryDetailViewModel
import kotlinx.coroutines.flow.collect
import java.io.File

@Composable
fun HistoryDetailPage(
    historyItemId: String,
    setupTopBar: (TopBarEvent) -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    historyDetailViewModel: HistoryDetailViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
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
            viewState.uiHistoryItem == null -> ShoppingWarfareEmptyScreen(stringResource(R.string.history_detail_empty_message))
            viewState.uiHistoryItem != null -> {
                viewState.uiHistoryItem!!.receiptPath?.let { receiptPath ->
                    Image(
                        painter = rememberImagePainter(
                            data = File(
                                context.filesDir,
                                "2021:11:27 01:23:20.jpg"
//                                "2021%3A11%3A27%2001%3A23%3A20.jpg"
                                // file:///data/user/0/com.marinj.shoppingwarfare/files/2021%3A11%3A27%2001%3A23%3A20.jpg
                            )
                        ),
                        contentDescription = stringResource(id = R.string.receipt_image_content_description)
                    )
                }
            }
        }
    }
}
