package com.example.datafactory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datafactory.data.model.Orden
import com.example.datafactory.data.model.OrdenItem
import com.example.datafactory.data.remote.RetrofitClient
import com.example.datafactory.data.repository.OrdenRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CheckoutViewModel : ViewModel() {

    private val ordenRepository = OrdenRepository(RetrofitClient.instance)

    private val _createdOrder = MutableStateFlow<Orden?>(null)
    val createdOrder = _createdOrder.asStateFlow()

    fun createOrder(orden: Orden): Job {
        return viewModelScope.launch {
            val newOrder = ordenRepository.createOrden(orden)
            _createdOrder.value = newOrder
        }
    }
}