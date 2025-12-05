package com.example.datafactory.ui.screens.product

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.datafactory.ui.navigation.Screen
import com.example.datafactory.ui.viewmodel.ProductViewModel
import com.example.datafactory.ui.viewmodel.SortOrder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(navController: NavController, category: String?) {
    val viewModel: ProductViewModel = viewModel()
    val products by viewModel.filteredAndSortedProducts.collectAsState()
    val categories by viewModel.categories.collectAsState()
    
    LaunchedEffect(category) {
        viewModel.setCategoryFilter(category)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Productos") })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Filter and Sort Controls
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CategoryFilter(categories = categories, initialCategory = category, onCategorySelected = { viewModel.setCategoryFilter(it) })
                SortMenu(onSortSelected = { viewModel.setSortOrder(it) })
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products) { product ->
                    ProductCard(product = product) { productId ->
                        navController.navigate(Screen.ProductDetail.createRoute(productId))
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryFilter(categories: List<String>, initialCategory: String?, onCategorySelected: (String?) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(initialCategory ?: "Todas") }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedCategory ?: "Todas")
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("Todas") }, onClick = { 
                selectedCategory = "Todas"
                onCategorySelected(null)
                expanded = false 
            })
            categories.forEach { category ->
                DropdownMenuItem(text = { Text(category) }, onClick = { 
                    selectedCategory = category
                    onCategorySelected(category)
                    expanded = false 
                })
            }
        }
    }
}

@Composable
fun SortMenu(onSortSelected: (SortOrder) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedSortText by remember { mutableStateOf("Ordenar") }

    Box {
        OutlinedButton(onClick = { expanded = true }) {
            Text(selectedSortText)
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("Precio: Menor a Mayor") }, onClick = {
                selectedSortText = "Precio ↑"
                onSortSelected(SortOrder.PRICE_ASC)
                expanded = false
            })
            DropdownMenuItem(text = { Text("Precio: Mayor a Menor") }, onClick = {
                selectedSortText = "Precio ↓"
                onSortSelected(SortOrder.PRICE_DESC)
                expanded = false
            })
            DropdownMenuItem(text = { Text("Nombre: A-Z") }, onClick = {
                selectedSortText = "Nombre ↑"
                onSortSelected(SortOrder.NAME_ASC)
                expanded = false
            })
            DropdownMenuItem(text = { Text("Nombre: Z-A") }, onClick = {
                selectedSortText = "Nombre ↓"
                onSortSelected(SortOrder.NAME_DESC)
                expanded = false
            })
        }
    }
}