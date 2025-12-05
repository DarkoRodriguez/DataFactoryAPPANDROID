package com.example.datafactory.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.datafactory.ui.viewmodel.AdminOrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminOrderDetailScreen(navController: NavController, orderId: Long) {
    val viewModel: AdminOrderViewModel = viewModel()
    val order by viewModel.order.collectAsState()

    LaunchedEffect(orderId) {
        viewModel.getOrder(orderId)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Detalle del Pedido #$orderId") })
        }
    ) { paddingValues ->
        order?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                Text("Cliente: ${it.nombreCompleto}")
                Text("Estado: ${it.estado}")
                Spacer(modifier = Modifier.height(16.dp))
                OrderStatusDropdown(currentStatus = it.estado) { newStatus ->
                    viewModel.updateOrderStatus(orderId, newStatus)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderStatusDropdown(currentStatus: String, onStatusSelected: (String) -> Unit) {
    val options = listOf("PENDIENTE", "EN PROCESO", "COMPLETADO", "CANCELADO")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(currentStatus) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text("Estado del pedido") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(text = selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        onStatusSelected(selectionOption)
                        expanded = false
                    }
                )
            }
        }
    }
}
