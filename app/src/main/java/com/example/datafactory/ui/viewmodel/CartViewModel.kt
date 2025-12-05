package com.example.datafactory.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.datafactory.data.local.SessionManager
import com.example.datafactory.data.model.CarritoItem
import com.example.datafactory.data.model.Producto
import com.example.datafactory.data.remote.RetrofitClient
import com.example.datafactory.data.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val carritoRepository = CarritoRepository(RetrofitClient.instance)

    private val _cartItems = MutableStateFlow<List<CarritoItem>>(emptyList())
    val cartItems = _cartItems.asStateFlow()

    init {
        viewModelScope.launch {
            SessionManager.currentUser.collect { user ->
                if (user != null) {
                    loadCartItems(user.id!!)
                } else {
                    _cartItems.value = emptyList()
                }
            }
        }
    }

    private fun loadCartItems(userId: Long) {
        viewModelScope.launch {
            val allItems = carritoRepository.getCarritoItems()
            _cartItems.value = allItems?.filter { it.usuarioId == userId } ?: emptyList()
        }
    }

    fun addToCart(product: Producto) {
        viewModelScope.launch {
            val userId = SessionManager.currentUser.value?.id ?: return@launch

            val existingItem = _cartItems.value.find { it.productoId == product.id }
            if (existingItem != null) {
                val updatedItem = existingItem.copy(cantidad = existingItem.cantidad + 1)
                carritoRepository.updateCarritoItem(existingItem.id!!, updatedItem)
            } else {
                val newItem = CarritoItem(
                    productoId = product.id!!,
                    nombre = product.nombre,
                    precio = product.precio,
                    cantidad = 1,
                    imagen = product.imagen,
                    usuarioId = userId,
                    sessionId = null
                )
                carritoRepository.createCarritoItem(newItem)
            }
            loadCartItems(userId)
        }
    }

    fun removeItem(item: CarritoItem) {
        viewModelScope.launch {
            val userId = SessionManager.currentUser.value?.id ?: return@launch
            item.id?.let { 
                carritoRepository.deleteCarritoItem(it)
                loadCartItems(userId)
            }
        }
    }

    fun increaseQuantity(item: CarritoItem) {
        viewModelScope.launch {
            val userId = SessionManager.currentUser.value?.id ?: return@launch
            val updatedItem = item.copy(cantidad = item.cantidad + 1)
            item.id?.let { 
                carritoRepository.updateCarritoItem(it, updatedItem)
                loadCartItems(userId)
            }
        }
    }

    fun decreaseQuantity(item: CarritoItem) {
        viewModelScope.launch {
            val userId = SessionManager.currentUser.value?.id ?: return@launch
            if (item.cantidad > 1) {
                val updatedItem = item.copy(cantidad = item.cantidad - 1)
                item.id?.let { 
                    carritoRepository.updateCarritoItem(it, updatedItem)
                    loadCartItems(userId)
                }
            } else {
                item.id?.let { 
                    carritoRepository.deleteCarritoItem(it)
                    loadCartItems(userId)
                }
            }
        }
    }
    
    fun clearCart() {
        viewModelScope.launch {
            val userId = SessionManager.currentUser.value?.id ?: return@launch
            _cartItems.value.forEach { item ->
                item.id?.let { carritoRepository.deleteCarritoItem(it) }
            }
            loadCartItems(userId)
        }
    }
}