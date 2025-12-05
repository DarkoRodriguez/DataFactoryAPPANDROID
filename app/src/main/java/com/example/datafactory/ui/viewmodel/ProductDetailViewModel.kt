package com.example.datafactory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datafactory.data.model.Producto
import com.example.datafactory.data.remote.RetrofitClient
import com.example.datafactory.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductDetailViewModel : ViewModel() {

    private val productoRepository = ProductoRepository(RetrofitClient.instance)

    private val _producto = MutableStateFlow<Producto?>(null)
    val producto: StateFlow<Producto?> = _producto

    fun getProducto(productId: Long) {
        viewModelScope.launch {
            val product = productoRepository.getProducto(productId)
            if (product != null) {
                _producto.value = product
            }
        }
    }
}