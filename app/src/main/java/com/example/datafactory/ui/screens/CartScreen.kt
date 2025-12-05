package com.example.datafactory.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.datafactory.ui.navigation.Screen
import com.example.datafactory.ui.screens.cart.CartItem
import com.example.datafactory.ui.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val total = cartItems.sumOf { it.precio * it.cantidad }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Carrito de Compras") })
        }
    ) { paddingValues ->
        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Carrito Vacío",
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Tu carrito está vacío", style = MaterialTheme.typography.titleMedium)
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(cartItems) { item ->
                        CartItem(
                            item = item,
                            onIncrease = { cartViewModel.increaseQuantity(item) },
                            onDecrease = { cartViewModel.decreaseQuantity(item) },
                            onRemove = { cartViewModel.removeItem(item) }
                        )
                        HorizontalDivider()
                    }
                }

                // Total and Checkout Button
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text("$${String.format("%.2f", total)}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { navController.navigate(Screen.Checkout.route) },
                            modifier = Modifier.fillMaxWidth().height(48.dp)
                        ) {
                            Text("Continuar Compra")
                        }
                    }
                }
            }
        }
    }
}