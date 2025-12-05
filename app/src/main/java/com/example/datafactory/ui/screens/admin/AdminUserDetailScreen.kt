package com.example.datafactory.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email

import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.datafactory.ui.viewmodel.AdminUserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUserDetailScreen(navController: NavController, userId: Long) {
    val viewModel: AdminUserViewModel = viewModel()
    val user by viewModel.selectedUser.collectAsState()

    LaunchedEffect(userId) {
        viewModel.getUser(userId)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Detalle del Usuario") })
        }
    ) { paddingValues ->
        user?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Header con Nombre de usuario
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Person, contentDescription = "User Icon", modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = it.nombre,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 16.dp))

                // Datos del usuario
                UserInfoRow(icon = Icons.Filled.Email, label = "Email", value = it.email)
                UserInfoRow(icon = Icons.Filled.Lock, label = "Contraseña", value = "********")
                UserInfoRow(icon = Icons.Filled.Phone, label = "Teléfono", value = it.telefono.toString())
                UserInfoRow(icon = Icons.Filled.Place, label = "Región", value = it.region)
                UserInfoRow(icon = Icons.Filled.Place, label = "Comuna", value = it.comuna)
            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun UserInfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    ListItem(
        headlineContent = { Text(value) },
        supportingContent = { Text(label) },
        leadingContent = { Icon(icon, contentDescription = label) }
    )
    Divider()
}