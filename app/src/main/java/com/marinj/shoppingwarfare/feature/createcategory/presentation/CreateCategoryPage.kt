package com.marinj.shoppingwarfare.feature.createcategory.presentation

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
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.marinj.shoppingwarfare.R
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEffect
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEffect.CreateCategorySuccess
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEvent.OnBackgroundColorChanged
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEvent.OnCategoryNameChanged
import com.marinj.shoppingwarfare.feature.createcategory.presentation.model.CreateCategoryEvent.OnCreateCategoryClicked
import com.marinj.shoppingwarfare.feature.createcategory.presentation.viewmodel.CreateCategoryViewModel
import kotlinx.coroutines.flow.collect

const val CREATE_CATEGORY_ROUTE = "createCategory"

@Composable
fun CreateCategoryPage(
    createCategoryViewModel: CreateCategoryViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {
    val viewState by createCategoryViewModel.createCategoryViewState.collectAsState()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = createCategoryViewModel.createCategoryEffect) {
        createCategoryViewModel.createCategoryEffect.collect { viewEffect ->
            when (viewEffect) {
                CreateCategorySuccess -> scaffoldState.snackbarHostState.showSnackbar("Success", actionLabel = "Navigate back")
                is CreateCategoryEffect.CreateCategoryFailure -> scaffoldState.snackbarHostState.showSnackbar(
                    viewEffect.errorMessage,
                    actionLabel = "Dismiss"
                )
            }.also {
                if (viewEffect is CreateCategorySuccess) {
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
                    onColorChanged = {
                        createCategoryViewModel.onEvent(OnBackgroundColorChanged(it))
                    },
                    selectedColor = viewState.backgroundColor,
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
