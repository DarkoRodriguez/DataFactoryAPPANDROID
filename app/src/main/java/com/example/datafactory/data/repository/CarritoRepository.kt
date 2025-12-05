package com.example.datafactory.data.repository

import com.example.datafactory.data.model.CarritoItem
import com.example.datafactory.data.remote.ApiService

class CarritoRepository(private val apiService: ApiService) {

    suspend fun getCarritoItems(): List<CarritoItem>? {
        val response = apiService.getCarritoItems()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun createCarritoItem(carritoItem: CarritoItem): CarritoItem? {
        val response = apiService.createCarritoItem(carritoItem)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun updateCarritoItem(id: Long, carritoItem: CarritoItem): CarritoItem? {
        val response = apiService.updateCarritoItem(id, carritoItem)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun deleteCarritoItem(id: Long): Boolean {
        val response = apiService.deleteCarritoItem(id)
        return response.isSuccessful
    }
}