package com.example.datafactory.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email

import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.datafactory.ui.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val viewModel: ProfileViewModel = viewModel()
    val user by viewModel.user.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mi Perfil") })
        }
    ) { paddingValues ->
        user?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header with user name
                Icon(
                    Icons.Filled.Person,
                    contentDescription = "User Icon",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = it.nombre,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))
                Divider()

                // User data
                ProfileInfoRow(icon = Icons.Filled.Email, label = "Email", value = it.email)
                ProfileInfoRow(icon = Icons.Filled.Phone, label = "Teléfono", value = it.telefono.toString())
                ProfileInfoRow(icon = Icons.Filled.Place, label = "Región", value = it.region)
                ProfileInfoRow(icon = Icons.Filled.Place, label = "Comuna", value = it.comuna)
            }
        } ?: run {
            // Show a message if the user is not logged in
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No se ha iniciado sesión.")
            }
        }
    }
}

@Composable
fun ProfileInfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    ListItem(
        headlineContent = { Text(value) },
        supportingContent = { Text(label) },
        leadingContent = { Icon(icon, contentDescription = label) }
    )
    Divider()
}