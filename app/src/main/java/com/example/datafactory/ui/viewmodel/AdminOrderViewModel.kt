package com.example.datafactory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datafactory.data.model.Orden
import com.example.datafactory.data.remote.RetrofitClient
import com.example.datafactory.data.repository.OrdenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminOrderViewModel : ViewModel() {

    private val ordenRepository = OrdenRepository(RetrofitClient.instance)

    private val _orders = MutableStateFlow<List<Orden>>(emptyList())
    val orders: StateFlow<List<Orden>> = _orders

    private val _order = MutableStateFlow<Orden?>(null)
    val order: StateFlow<Orden?> = _order

    fun getOrders() {
        viewModelScope.launch {
            val orderList = ordenRepository.getOrdenes()
            if (orderList != null) {
                _orders.value = orderList
            }
        }
    }

    fun getOrder(orderId: Long) {
        viewModelScope.launch {
            val fetchedOrder = ordenRepository.getOrden(orderId)
            if (fetchedOrder != null) {
                _order.value = fetchedOrder
            }
        }
    }

    fun updateOrderStatus(orderId: Long, newStatus: String) {
        viewModelScope.launch {
            val currentOrder = _order.value
            if (currentOrder != null) {
                val updatedOrder = currentOrder.copy(estado = newStatus)
                ordenRepository.updateOrden(orderId, updatedOrder)
                // Refresh the order details
                getOrder(orderId)
            }
        }
    }
}