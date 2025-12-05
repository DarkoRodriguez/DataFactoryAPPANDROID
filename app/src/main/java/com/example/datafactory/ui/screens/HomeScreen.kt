package com.example.datafactory.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.datafactory.R
import com.example.datafactory.ui.navigation.Screen
import com.example.datafactory.ui.screens.home.CategoryList
import com.example.datafactory.ui.screens.home.FeaturedProducts
import com.example.datafactory.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    val homeViewModel: HomeViewModel = viewModel()
    val featuredProducts by homeViewModel.featuredProducts.collectAsState()
    val categories by homeViewModel.categories.collectAsState()

    val pagerState = rememberPagerState(pageCount = { featuredProducts.size })

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home") },
                navigationIcon = {
                    IconButton(onClick = { 
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            // Welcome Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_empresa),
                        contentDescription = "Logo de DataFactory",
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = "Bienvenido a Data Factory",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Tu tienda para aprender a programar",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Banner Carousel
            if (featuredProducts.isNotEmpty()) {
                item {
                    HorizontalPager(
                        state = pagerState,
                        contentPadding = PaddingValues(horizontal = 32.dp),
                        modifier = Modifier.height(200.dp)
                    ) { page ->
                        val product = featuredProducts[page]
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable { product.id?.let { navController.navigate(Screen.ProductDetail.createRoute(it)) } }
                        ) {
                            AsyncImage(
                                model = product.imagen,
                                contentDescription = product.nombre,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
            }

            // Category Buttons
            item {
                CategoryList(categories = categories) { category ->
                    navController.navigate(Screen.ProductList.createRoute(category))
                }
            }
            
            // Featured Products
            item {
                 Text(
                    text = "Productos Destacados", 
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
                )
            }
            item {
                FeaturedProducts(products = featuredProducts) { productId ->
                    navController.navigate(Screen.ProductDetail.createRoute(productId))
                }
            }
        }
    }
}