package com.marinj.shoppingwarfare.feature.createcategory.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

const val CREATE_CATEGORY_ROUTE = "createCategory"

@Composable
fun CreateCategoryPage() {
    Scaffold {
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
                var categoryName by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = categoryName,
                    label = { Text("Category name") },
                    onValueChange = { categoryName = it }
                )

                //TODO  Later down the line allow the user to select a picture for the category
                ColorPicker(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 24.dp)
                )

                Button(
                    modifier = Modifier.padding(top = 24.dp),
                    onClick = { /*TODO*/ }) {
                    Text("Create category")
                }
            }
        }
    }
}