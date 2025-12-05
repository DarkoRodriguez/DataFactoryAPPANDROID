package com.example.datafactory.ui.screens.admin

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.datafactory.ui.viewmodel.CategoryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCategoryListScreen(navController: NavController) {
    val viewModel: CategoryViewModel = viewModel()
    val categories by viewModel.categories.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Categorías") })
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(categories) { category ->
                ListItem(
                    headlineContent = { Text(category) },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = "Category Icon",
                        )
                    }
                )
                Divider()
            }
        }
    }
}