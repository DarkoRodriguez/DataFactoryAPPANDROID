package com.example.datafactory.ui.screens.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.datafactory.ui.navigation.Screen
import com.example.datafactory.ui.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminProductListScreen(navController: NavController) {
    val viewModel: ProductViewModel = viewModel()
    val productos by viewModel.filteredAndSortedProducts.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Productos") },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.AdminProductCreate.route) }) {
                        Icon(Icons.Filled.Add, contentDescription = "Agregar producto")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(productos) { producto ->
                ListItem(
                    headlineContent = { Text(producto.nombre) },
                    supportingContent = { Text(producto.categoria) },
                    leadingContent = {
                        AsyncImage(
                            model = producto.imagen,
                            contentDescription = producto.nombre,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    },
                    trailingContent = {
                        Row {
                            IconButton(onClick = { 
                                producto.id?.let { navController.navigate(Screen.AdminProductEdit.createRoute(it)) }
                            }) {
                                Icon(Icons.Filled.Edit, contentDescription = "Editar producto")
                            }
                            IconButton(onClick = { 
                                producto.id?.let { viewModel.deleteProducto(it) } 
                            }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Eliminar producto")
                            }
                        }
                    },
                    modifier = Modifier.clickable { 
                        producto.id?.let { navController.navigate(Screen.AdminProductEdit.createRoute(it)) }
                    }
                )
                Divider()
            }
        }
    }
}