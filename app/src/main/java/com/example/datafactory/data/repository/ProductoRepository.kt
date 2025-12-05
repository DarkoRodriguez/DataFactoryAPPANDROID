package com.example.datafactory.data.repository

import com.example.datafactory.data.model.Producto
import com.example.datafactory.data.remote.ApiService

class ProductoRepository(private val apiService: ApiService) {

    suspend fun getProductos(): List<Producto>? {
        val response = apiService.getProductos()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun getProducto(id: Long): Producto? {
        val response = apiService.getProducto(id)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun createProducto(producto: Producto): Producto? {
        val response = apiService.createProducto(producto)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun updateProducto(id: Long, producto: Producto): Producto? {
        val response = apiService.updateProducto(id, producto)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun deleteProducto(id: Long): Boolean {
        val response = apiService.deleteProducto(id)
        return response.isSuccessful
    }
}