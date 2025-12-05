package com.example.datafactory.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "carrito_items")
data class CarritoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @SerializedName("usuario_id")
    val usuarioId: Long?,
    val sessionId: String?,
    @SerializedName("producto_id")
    val productoId: Long,
    val nombre: String,
    val imagen: String,
    val precio: Double,
    val cantidad: Int
)