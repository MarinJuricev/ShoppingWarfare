package com.marinj.shoppingwarfare.feature.category.createcategory.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.R.string
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CreateCategoryTopBar
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.components.ColorPicker
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnBackgroundColorChanged
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnCategoryNameChanged
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnCreateCategoryClicked
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnTitleColorChanged
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewEffect
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewEffect.CreateCategoryViewSuccess
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewState
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.viewmodel.CreateCategoryViewModel
import com.marinj.shoppingwarfare.ui.PrimaryElevatedButton
import com.marinj.shoppingwarfare.ui.SWCard
import com.marinj.shoppingwarfare.ui.SWScaffold
import com.marinj.shoppingwarfare.ui.SWTextField
import com.marinj.shoppingwarfare.ui.SnackBarState

@Composable
fun CreateCategoryScreen(
    navigateBack: () -> Unit,
    setupTopBar: (TopBarEvent) -> Unit,
    createCategoryViewModel: CreateCategoryViewModel = hiltViewModel(),
) {
    var snackBarState by remember { mutableStateOf<SnackBarState?>(null) }
    val viewState by createCategoryViewModel.createCategoryViewState.collectAsState()
    val currentContext = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        setupTopBar(CreateCategoryTopBar())
    }

    LaunchedEffect(key1 = createCategoryViewModel.createCategoryEffect) {
        createCategoryViewModel.createCategoryEffect.collect { viewEffect ->
            snackBarState = when (viewEffect) {
                CreateCategoryViewSuccess -> SnackBarState(
                    message = currentContext.getString(string.success),
                    actionLabel = currentContext.getString(string.navigate_back),
                    action = navigateBack,
                )

                is CreateCategoryViewEffect.CreateCategoryViewFailure -> SnackBarState(
                    message = viewEffect.errorMessage,
                    actionLabel = currentContext.getString(R.string.dismiss),
                )
            }
        }
    }

    CreateCategoryScreen(
        viewState = viewState,
        onEvent = createCategoryViewModel::onEvent,
        snackBarState = snackBarState,
    )
}

@Composable
private fun CreateCategoryScreen(
    viewState: CreateCategoryViewState,
    onEvent: (CreateCategoryEvent) -> Unit,
    snackBarState: SnackBarState?,
) {
    SWScaffold(
        snackBarState = snackBarState,
    ) { paddingValues ->
        SWCard(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .wrapContentSize(),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SWTextField(
                    value = viewState.categoryName,
                    singleLine = true,
                    label = { Text(stringResource(string.category_name)) },
                    onValueChange = { onEvent(OnCategoryNameChanged(it)) },
                )
                ColorPicker(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 24.dp),
                    title = stringResource(string.category_background_color),
                    onColorChanged = {
                        onEvent(OnBackgroundColorChanged(it))
                    },
                    selectedColor = viewState.backgroundColor,
                    colors = viewState.availableColors,
                )
                ColorPicker(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 24.dp),
                    title = stringResource(string.category_title_color),
                    onColorChanged = {
                        onEvent(OnTitleColorChanged(it))
                    },
                    selectedColor = viewState.titleColor,
                    colors = viewState.availableColors,
                )
                PrimaryElevatedButton(
                    modifier = Modifier.padding(top = 24.dp),
                    text = stringResource(string.create_category),
                    onClick = { onEvent(OnCreateCategoryClicked) },
                )
            }
        }
    }
}
