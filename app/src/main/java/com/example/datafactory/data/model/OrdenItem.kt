package com.example.datafactory.data.model

import com.google.gson.annotations.SerializedName

data class OrdenItem(
    val id: Long? = null,
    @SerializedName("orden_id")
    val ordenId: Long? = null,
    @SerializedName("producto_id")
    val productoId: Long,
    val nombre: String,
    val cantidad: Int,
    @SerializedName("precio_unitario")
    val precioUnitario: Double,
    val subtotal: Double
)