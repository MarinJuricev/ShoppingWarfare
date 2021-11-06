package com.marinj.shoppingwarfare.feature.category.createcategory.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.R.string
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent
import com.marinj.shoppingwarfare.core.viewmodel.topbar.TopBarEvent.CreateCategoryTopBar
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.components.ColorPicker
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnBackgroundColorChanged
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnCategoryNameChanged
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnCreateCategoryClicked
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryEvent.OnTitleColorChanged
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewEffect
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.model.CreateCategoryViewEffect.CreateCategoryViewSuccess
import com.marinj.shoppingwarfare.feature.category.createcategory.presentation.viewmodel.CreateCategoryViewModel
import kotlinx.coroutines.flow.collect

const val CREATE_CATEGORY_ROUTE = "createCategory"

@Composable
fun CreateCategoryPage(
    navigateBack: () -> Unit,
    setupTopBar: (TopBarEvent) -> Unit,
    createCategoryViewModel: CreateCategoryViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val viewState by createCategoryViewModel.createCategoryViewState.collectAsState()
    val currentContext = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        setupTopBar(CreateCategoryTopBar())
    }

    LaunchedEffect(key1 = createCategoryViewModel.createCategoryEffect) {
        createCategoryViewModel.createCategoryEffect.collect { viewEffect ->
            when (viewEffect) {
                CreateCategoryViewSuccess -> scaffoldState.snackbarHostState.showSnackbar(
                    message = currentContext.getString(R.string.success),
                    actionLabel = currentContext.getString(R.string.navigate_back),
                )
                is CreateCategoryViewEffect.CreateCategoryViewFailure -> scaffoldState.snackbarHostState.showSnackbar(
                    message = viewEffect.errorMessage,
                    actionLabel = currentContext.getString(R.string.dismiss)
                )
            }.also {
                if (viewEffect is CreateCategoryViewSuccess) {
                    navigateBack()
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = viewState.categoryName,
                    maxLines = 1,
                    label = { Text(stringResource(R.string.category_name)) },
                    onValueChange = { createCategoryViewModel.onEvent(OnCategoryNameChanged(it)) }
                )
                // TODO  Later down the line allow the user to select a picture for the category
                ColorPicker(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 24.dp),
                    title = stringResource(string.category_background_color),
                    onColorChanged = {
                        createCategoryViewModel.onEvent(OnBackgroundColorChanged(it))
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
                        createCategoryViewModel.onEvent(OnTitleColorChanged(it))
                    },
                    selectedColor = viewState.titleColor,
                    colors = viewState.availableColors,
                )
                Button(
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = { createCategoryViewModel.onEvent(OnCreateCategoryClicked) }
                ) {
                    Text(stringResource(R.string.create_category))
                }
            }
        }
    }
}
