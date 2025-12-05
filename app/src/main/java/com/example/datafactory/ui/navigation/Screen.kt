package com.example.datafactory.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Registro : Screen("registro")
    object Home : Screen("home")
    object ProductList : Screen("product_list?category={category}") {
        fun createRoute(category: String? = null): String {
            return if (category != null) {
                "product_list?category=$category"
            } else {
                "product_list"
            }
        }
    }
    object ProductDetail : Screen("product_detail/{productId}") {
        fun createRoute(productId: Long) = "product_detail/$productId"
    }
    object Cart : Screen("cart")
    object Checkout : Screen("checkout")
    object Confirmation : Screen("confirmation")
    object Profile : Screen("profile")
    object Settings : Screen("settings")

    // Admin
    object AdminLogin : Screen("admin_login")
    object AdminDashboard : Screen("admin_dashboard")
    object AdminProductList : Screen("admin_product_list")
    object AdminProductCreate : Screen("admin_product_create")
    object AdminProductEdit : Screen("admin_product_edit/{productId}") {
        fun createRoute(productId: Long) = "admin_product_edit/$productId"
    }
    object AdminCategoryList : Screen("admin_category_list")
    object AdminCategoryCreate : Screen("admin_category_create")
    object AdminCategoryEdit : Screen("admin_category_edit/{categoryId}") {
        fun createRoute(categoryId: Long) = "admin_category_edit/$categoryId"
    }
    object AdminOrderList : Screen("admin_order_list")
    object AdminOrderDetail : Screen("admin_order_detail/{orderId}") {
        fun createRoute(orderId: Long) = "admin_order_detail/$orderId"
    }
    object AdminUserList : Screen("admin_user_list")
    object AdminUserDetail : Screen("admin_user_detail/{userId}") {
        fun createRoute(userId: Long) = "admin_user_detail/$userId"
    }
}