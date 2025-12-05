package com.example.datafactory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.navigation.compose.rememberNavController
import com.example.datafactory.ui.navigation.AppNavigation
import com.example.datafactory.ui.navigation.NavDrawer
import com.example.datafactory.ui.theme.DataFactoryTheme
import com.example.datafactory.ui.viewmodel.CartViewModel

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {

    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DataFactoryTheme {
                val navController = rememberNavController()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                NavDrawer(navController = navController, drawerState = drawerState) {
                    AppNavigation(
                        navController = navController, 
                        drawerState = drawerState,
                        cartViewModel = cartViewModel
                    )
                }
            }
        }
    }
}