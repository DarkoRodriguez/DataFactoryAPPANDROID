package com.example.datafactory.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.datafactory.data.local.SessionManager
import com.example.datafactory.data.model.Orden
import com.example.datafactory.data.model.OrdenItem
import com.example.datafactory.ui.navigation.Screen
import com.example.datafactory.ui.viewmodel.CartViewModel
import com.example.datafactory.ui.viewmodel.CheckoutViewModel
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavController, cartViewModel: CartViewModel, checkoutViewModel: CheckoutViewModel = viewModel()) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val currentUser by SessionManager.currentUser.collectAsState()
    val createdOrder by checkoutViewModel.createdOrder.collectAsState()
    val scope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var calle by remember { mutableStateOf("") }
    var departamento by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }
    var comuna by remember { mutableStateOf("") }
    var indicaciones by remember { mutableStateOf("") }

    LaunchedEffect(currentUser) {
        currentUser?.let {
            nombre = it.nombre
            correo = it.email
            region = it.region
            comuna = it.comuna
        }
    }

    LaunchedEffect(createdOrder) {
        createdOrder?.let {
            cartViewModel.clearCart()
            navController.navigate("confirmation/${it.numeroOrden}")
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Finalizar Compra") }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Order Summary
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Resumen del Pedido", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    cartItems.forEach { item ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("${item.cantidad}x ${item.nombre}")
                            Text("$${item.precio * item.cantidad}")
                        }
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Total", fontWeight = FontWeight.Bold)
                        Text("$${cartItems.sumOf { it.precio * it.cantidad }}", fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Shipping Information
            Text("Información de Envío", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = apellidos, onValueChange = { apellidos = it }, label = { Text("Apellidos") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = correo, onValueChange = { correo = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = calle, onValueChange = { calle = it }, label = { Text("Calle") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = departamento, onValueChange = { departamento = it }, label = { Text("Departamento (Opcional)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = region, onValueChange = { region = it }, label = { Text("Región") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = comuna, onValueChange = { comuna = it }, label = { Text("Comuna") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = indicaciones, onValueChange = { indicaciones = it }, label = { Text("Indicaciones adicionales (Opcional)") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        val newOrder = Orden(
                            usuarioId = currentUser?.id!!,
                            numeroOrden = UUID.randomUUID().toString().take(8).uppercase(),
                            total = cartItems.sumOf { it.precio * it.cantidad },
                            nombreCompleto = nombre,
                            apellidos = apellidos,
                            correo = correo,
                            calle = calle,
                            departamento = departamento,
                            region = region,
                            comuna = comuna,
                            indicaciones = indicaciones,
                            items = cartItems.map { 
                                OrdenItem(
                                    productoId = it.productoId,
                                    nombre = it.nombre,
                                    cantidad = it.cantidad,
                                    precioUnitario = it.precio,
                                    subtotal = it.precio * it.cantidad
                                )
                            }
                        )
                        checkoutViewModel.createOrder(newOrder).join()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Pagar")
            }
        }
    }
}