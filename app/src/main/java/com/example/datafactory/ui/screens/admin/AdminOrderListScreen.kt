package com.example.datafactory.ui.screens.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.datafactory.ui.navigation.Screen
import com.example.datafactory.ui.viewmodel.AdminOrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminOrderListScreen(navController: NavController) {
    val viewModel: AdminOrderViewModel = viewModel()
    val orders by viewModel.orders.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getOrders()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Pedidos") })
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(orders) { order ->
                ListItem(
                    headlineContent = { Text("Pedido #${order.numeroOrden}") },
                    supportingContent = { Text("Estado: ${order.estado}") },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Email,
                            contentDescription = "Order Icon",
                        )
                    },
                    modifier = Modifier.clickable { 
                        order.id?.let { orderId ->
                            navController.navigate(Screen.AdminOrderDetail.createRoute(orderId))
                        }
                    }
                )
                Divider()
            }
        }
    }
}