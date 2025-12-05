package com.example.datafactory.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoryItem(category: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = category,
            modifier = Modifier.padding(16.dp)
        )
    }
}