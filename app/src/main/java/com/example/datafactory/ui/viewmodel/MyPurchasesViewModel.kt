package com.example.datafactory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datafactory.data.local.SessionManager
import com.example.datafactory.data.model.Orden
import com.example.datafactory.data.remote.RetrofitClient
import com.example.datafactory.data.repository.OrdenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyPurchasesViewModel : ViewModel() {

    private val ordenRepository = OrdenRepository(RetrofitClient.instance)

    private val _myOrders = MutableStateFlow<List<Orden>>(emptyList())
    val myOrders = _myOrders.asStateFlow()

    init {
        viewModelScope.launch {
            SessionManager.currentUser.collect { user ->
                if (user != null) {
                    loadMyOrders(user.id!!)
                } else {
                    _myOrders.value = emptyList()
                }
            }
        }
    }

    private fun loadMyOrders(userId: Long) {
        viewModelScope.launch {
            val allOrders = ordenRepository.getOrdenes()
            _myOrders.value = allOrders?.filter { it.usuarioId == userId } ?: emptyList()
        }
    }
}