package com.example.datafactory.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavDrawerItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)