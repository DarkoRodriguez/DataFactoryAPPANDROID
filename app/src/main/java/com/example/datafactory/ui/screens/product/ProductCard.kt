package com.example.datafactory.ui.screens.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.datafactory.data.model.Producto

@Composable
fun ProductCard(product: Producto, onProductClick: (Long) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { 
                product.id?.let { productId ->
                    onProductClick(productId)
                }
            }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = product.imagen,
                contentDescription = product.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = product.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${product.precio}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}