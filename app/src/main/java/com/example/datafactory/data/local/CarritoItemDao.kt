package com.example.datafactory.data.local

import androidx.room.*
import com.example.datafactory.data.model.CarritoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoItemDao {

    @Query("SELECT * FROM carrito_items")
    fun getAll(): Flow<List<CarritoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CarritoItem)

    @Update
    suspend fun update(item: CarritoItem)

    @Delete
    suspend fun delete(item: CarritoItem)

    @Query("DELETE FROM carrito_items")
    suspend fun deleteAll()
}