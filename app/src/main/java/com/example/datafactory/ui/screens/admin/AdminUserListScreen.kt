package com.example.datafactory.ui.screens.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.datafactory.ui.navigation.Screen
import com.example.datafactory.ui.viewmodel.AdminUserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUserListScreen(navController: NavController) {
    val viewModel: AdminUserViewModel = viewModel()
    val users by viewModel.users.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getUsers()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Usuarios") })
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(users) { user ->
                ListItem(
                    headlineContent = { Text(user.nombre) },
                    supportingContent = { Text(user.email) },
                    leadingContent = {
                        Icon(
                            Icons.Filled.Person,
                            contentDescription = "User Icon",
                        )
                    },
                    modifier = Modifier.clickable {
                        user.id?.let { userId ->
                            navController.navigate(Screen.AdminUserDetail.createRoute(userId))
                        }
                    }
                )
                Divider()
            }
        }
    }
}