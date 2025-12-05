package com.example.datafactory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datafactory.data.model.Producto
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel : ViewModel() {

    private val productViewModel = ProductViewModel()

    val featuredProducts: StateFlow<List<Producto>> = productViewModel.filteredAndSortedProducts.map {
        it.filter { p -> p.oferta }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val categories: StateFlow<List<String>> = productViewModel.categories

}