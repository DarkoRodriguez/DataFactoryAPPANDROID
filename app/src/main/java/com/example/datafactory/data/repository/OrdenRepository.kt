package com.example.datafactory.data.repository

import com.example.datafactory.data.model.Orden
import com.example.datafactory.data.remote.ApiService

class OrdenRepository(private val apiService: ApiService) {

    suspend fun getOrdenes(): List<Orden>? {
        val response = apiService.getOrdenes()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun getOrden(id: Long): Orden? {
        val response = apiService.getOrden(id)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun createOrden(orden: Orden): Orden? {
        val response = apiService.createOrden(orden)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun updateOrden(id: Long, orden: Orden): Orden? {
        val response = apiService.updateOrden(id, orden)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun deleteOrden(id: Long): Boolean {
        val response = apiService.deleteOrden(id)
        return response.isSuccessful
    }
}