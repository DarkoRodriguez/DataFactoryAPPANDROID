package com.example.datafactory.ui.screens.purchase

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.datafactory.ui.navigation.Screen
import com.example.datafactory.ui.viewmodel.MyPurchasesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPurchasesScreen(navController: NavController) {
    val viewModel: MyPurchasesViewModel = viewModel()
    val myOrders by viewModel.myOrders.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis Compras") })
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(myOrders) { order ->
                ListItem(
                    headlineContent = { Text("Pedido #${order.numeroOrden}") },
                    supportingContent = { Text("Estado: ${order.estado}") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "Order Icon",
                        )
                    },
                    modifier = Modifier.clickable { 
                        // TODO: Navigate to purchase detail screen
                    }
                )
                Divider()
            }
        }
    }
}