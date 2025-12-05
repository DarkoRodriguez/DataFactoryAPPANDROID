package com.example.datafactory.data.repository

import com.example.datafactory.data.remote.ApiService

class CategoryRepository(private val apiService: ApiService) {

    suspend fun getCategories(): List<String>? {
        val response = apiService.getProductos()
        return if (response.isSuccessful) {
            response.body()?.map { it.categoria }?.distinct()
        } else {
            null
        }
    }

    // Simulate creating a category since there is no endpoint
    suspend fun createCategory(category: String): Boolean {
        return true
    }
}