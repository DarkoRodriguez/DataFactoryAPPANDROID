package com.example.datafactory.ui.screens.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.datafactory.data.model.Producto
import com.example.datafactory.ui.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminProductCreateScreen(navController: NavController) {
    val viewModel: ProductViewModel = viewModel()
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var imagen by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Crear producto") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = categoria, onValueChange = { categoria = it }, label = { Text("Categoría") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = stock, onValueChange = { stock = it }, label = { Text("Stock") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = imagen, onValueChange = { imagen = it }, label = { Text("Imagen (URL)") }, modifier = Modifier.fillMaxWidth())

            Button(onClick = { 
                scope.launch {
                    val newProduct = Producto(
                        nombre = nombre,
                        categoria = categoria,
                        precio = precio.toDouble(),
                        descripcion = descripcion,
                        stock = stock.toInt(),
                        imagen = imagen,
                        oferta = false,
                        descuento = 0.0
                    )
                    viewModel.createProducto(newProduct).join()
                    navController.popBackStack()
                }
            }) {
                Text("Crear producto")
            }
        }
    }
}