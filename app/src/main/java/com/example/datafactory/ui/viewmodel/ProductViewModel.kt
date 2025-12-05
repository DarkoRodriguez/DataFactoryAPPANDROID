package com.example.datafactory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datafactory.data.model.Producto
import com.example.datafactory.data.remote.RetrofitClient
import com.example.datafactory.data.repository.ProductoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class SortOrder {
    NONE,
    PRICE_ASC,
    PRICE_DESC,
    NAME_ASC,
    NAME_DESC
}

class ProductViewModel : ViewModel() {

    private val productoRepository = ProductoRepository(RetrofitClient.instance)

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())

    private val _selectedProduct = MutableStateFlow<Producto?>(null)
    val selectedProduct: StateFlow<Producto?> = _selectedProduct

    private val _categoryFilter = MutableStateFlow<String?>(null)
    private val _sortOrder = MutableStateFlow(SortOrder.NONE)

    val categories: StateFlow<List<String>> = _productos.map {
        it.map { p -> p.categoria }.distinct().sorted()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredAndSortedProducts: StateFlow<List<Producto>> = 
        combine(_productos, _categoryFilter, _sortOrder) { products, category, sortOrder ->
            val filteredList = if (category == null) {
                products
            } else {
                products.filter { it.categoria == category }
            }

            when (sortOrder) {
                SortOrder.PRICE_ASC -> filteredList.sortedBy { it.precio }
                SortOrder.PRICE_DESC -> filteredList.sortedByDescending { it.precio }
                SortOrder.NAME_ASC -> filteredList.sortedBy { it.nombre }
                SortOrder.NAME_DESC -> filteredList.sortedByDescending { it.nombre }
                SortOrder.NONE -> filteredList
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        getProductos()
    }

    fun getProductos() {
        viewModelScope.launch {
            _productos.value = productoRepository.getProductos() ?: emptyList()
        }
    }

    fun getProducto(productId: Long) {
        viewModelScope.launch {
            _selectedProduct.value = productoRepository.getProducto(productId)
        }
    }
    
    fun setCategoryFilter(category: String?) {
        _categoryFilter.value = category
    }

    fun setSortOrder(sortOrder: SortOrder) {
        _sortOrder.value = sortOrder
    }

    fun createProducto(producto: Producto): Job {
        return viewModelScope.launch {
            productoRepository.createProducto(producto)
            getProductos() 
        }
    }

    fun updateProducto(producto: Producto): Job {
        return viewModelScope.launch {
            producto.id?.let {
                productoRepository.updateProducto(it, producto)
                getProductos() 
            }
        }
    }

    fun deleteProducto(productId: Long) {
        viewModelScope.launch {
            productoRepository.deleteProducto(productId)
            getProductos() 
        }
    }
    
    fun clearSelectedProduct() {
        _selectedProduct.value = null
    }
}