package com.example.datafactory.data.repository

import com.example.datafactory.data.local.CarritoItemDao
import com.example.datafactory.data.model.CarritoItem
import kotlinx.coroutines.flow.Flow

class CartRepository(private val carritoItemDao: CarritoItemDao) {

    val allItems: Flow<List<CarritoItem>> = carritoItemDao.getAll()

    suspend fun insert(item: CarritoItem) {
        carritoItemDao.insert(item)
    }

    suspend fun update(item: CarritoItem) {
        carritoItemDao.update(item)
    }

    suspend fun delete(item: CarritoItem) {
        carritoItemDao.delete(item)
    }

    suspend fun deleteAll() {
        carritoItemDao.deleteAll()
    }
}