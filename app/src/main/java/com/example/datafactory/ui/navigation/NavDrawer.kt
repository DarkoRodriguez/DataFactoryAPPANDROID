package com.example.datafactory.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.datafactory.R
import com.example.datafactory.data.local.SessionManager
import kotlinx.coroutines.launch

@Composable
fun NavDrawer(
    navController: NavController,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val userRole = SessionManager.userRole

    val gesturesEnabled = currentRoute != Screen.Login.route && currentRoute != Screen.Registro.route

    val clientItems = listOf(
        NavDrawerItem("Home", Screen.Home.route, Icons.Filled.Home, Icons.Outlined.Home),
        NavDrawerItem("Productos", Screen.ProductList.route, Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart),
        NavDrawerItem("Perfil", Screen.Profile.route, Icons.Filled.Person, Icons.Outlined.Person),
        NavDrawerItem("Carrito", Screen.Cart.route, Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart),
        NavDrawerItem("Ajustes", Screen.Settings.route, Icons.Filled.Settings, Icons.Outlined.Settings),
        NavDrawerItem("Admin Login", Screen.AdminLogin.route, Icons.Filled.Settings, Icons.Outlined.Settings)
    )

    val adminItems = listOf(
        NavDrawerItem("Dashboard", Screen.AdminDashboard.route, Icons.Filled.Info, Icons.Outlined.Info),
        NavDrawerItem("Productos", Screen.AdminProductList.route, Icons.Filled.List, Icons.Outlined.List),
        NavDrawerItem("Categorías", Screen.AdminCategoryList.route, Icons.Filled.Star, Icons.Outlined.Star),
        NavDrawerItem("Pedidos", Screen.AdminOrderList.route, Icons.Filled.Email, Icons.Outlined.Email),
        NavDrawerItem("Usuarios", Screen.AdminUserList.route, Icons.Filled.Person, Icons.Outlined.Person)
    )

    val items = if (userRole == "admin") adminItems else clientItems

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        drawerContent = {
            ModalDrawerSheet {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_empresa),
                        contentDescription = "Logo de la Empresa",
                        modifier = Modifier.size(100.dp).padding(top = 16.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                }
                items.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(if (currentRoute == item.route) item.selectedIcon else item.unselectedIcon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Divider(modifier = Modifier.padding(bottom = 8.dp))
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Close, contentDescription = "Cerrar sesión") },
                    label = { Text("Cerrar sesión") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        SessionManager.logout()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        },
        content = content
    )
}