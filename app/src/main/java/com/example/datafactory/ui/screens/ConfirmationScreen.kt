package com.example.datafactory.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.datafactory.ui.navigation.Screen

@Composable
fun ConfirmationScreen(navController: NavController, orderId: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Filled.CheckCircle, contentDescription = "Success", modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))
        Text("¡Pedido realizado con éxito!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Número de pedido: $orderId")
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate(Screen.Home.route) }) {
            Text("Volver al inicio")
        }
    }
}