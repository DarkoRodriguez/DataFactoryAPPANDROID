package com.example.datafactory.ui.navigation

import androidx.compose.animation.*
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.datafactory.ui.screens.*
import com.example.datafactory.ui.screens.admin.*
import com.example.datafactory.ui.screens.product.ProductListScreen
import com.example.datafactory.ui.viewmodel.CartViewModel
import com.example.datafactory.ui.viewmodel.CheckoutViewModel

@Composable
fun AppNavigation(
    navController: NavHostController, 
    drawerState: DrawerState, 
    cartViewModel: CartViewModel
) {
    NavHost(
        navController = navController, 
        startDestination = Screen.Login.route,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() },
        popExitTransition = { fadeOut() }
    ) {
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Registro.route) {
            RegistroScreen(navController = navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController = navController, drawerState = drawerState)
        }
        composable(
            route = Screen.ProductList.route,
            arguments = listOf(navArgument("category") { 
                type = NavType.StringType 
                nullable = true
            })
        ) { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            ProductListScreen(navController = navController, category = category)
        }
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.LongType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getLong("productId")
            if (productId != null) {
                ProductDetailScreen(
                    navController = navController, 
                    productId = productId,
                    cartViewModel = cartViewModel
                )
            }
        }
        composable(Screen.Cart.route) {
            CartScreen(navController = navController, cartViewModel = cartViewModel)
        }
        composable(Screen.Checkout.route) {
            val checkoutViewModel: CheckoutViewModel = viewModel()
            CheckoutScreen(
                navController = navController, 
                cartViewModel = cartViewModel,
                checkoutViewModel = checkoutViewModel
            )
        }
        composable(
            route = "confirmation/{orderId}",
            arguments = listOf(navArgument("orderId") { type = NavType.StringType })
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getString("orderId")
            if (orderId != null) {
                ConfirmationScreen(navController = navController, orderId = orderId)
            }
        }
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }

        // Admin
        composable(Screen.AdminLogin.route) {
            AdminLoginScreen(navController = navController)
        }
        composable(Screen.AdminDashboard.route) {
            AdminDashboardScreen(navController = navController)
        }
        composable(Screen.AdminProductList.route) {
            AdminProductListScreen(navController = navController)
        }
        composable(Screen.AdminProductCreate.route) {
            AdminProductCreateScreen(navController = navController)
        }
        composable(
            route = Screen.AdminProductEdit.route,
            arguments = listOf(navArgument("productId") { type = NavType.LongType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getLong("productId")
            if (productId != null) {
                AdminProductEditScreen(navController = navController, productId = productId)
            }
        }
        composable(Screen.AdminCategoryList.route) {
            AdminCategoryListScreen(navController = navController)
        }
        composable(Screen.AdminCategoryCreate.route) {
            AdminCategoryCreateScreen(navController = navController)
        }
        composable(Screen.AdminOrderList.route) {
            AdminOrderListScreen(navController = navController)
        }
        composable(
            route = Screen.AdminOrderDetail.route,
            arguments = listOf(navArgument("orderId") { type = NavType.LongType })
        ) { backStackEntry ->
            val orderId = backStackEntry.arguments?.getLong("orderId")
            if (orderId != null) {
                AdminOrderDetailScreen(navController = navController, orderId = orderId)
            }
        }
        composable(Screen.AdminUserList.route) {
            AdminUserListScreen(navController = navController)
        }
        composable(
            route = Screen.AdminUserDetail.route,
            arguments = listOf(navArgument("userId") { type = NavType.LongType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getLong("userId")
            if (userId != null) {
                AdminUserDetailScreen(navController = navController, userId = userId)
            }
        }
    }
}