package com.example.datafactory.data.model

import com.google.gson.annotations.SerializedName

data class Orden(
    val id: Long? = null,
    @SerializedName("usuario_id")
    val usuarioId: Long,
    @SerializedName("numero_orden")
    val numeroOrden: String,
    val estado: String = "PENDIENTE",
    val total: Double,
    @SerializedName("nombre_completo")
    val nombreCompleto: String,
    val apellidos: String,
    val correo: String,
    val calle: String,
    val departamento: String,
    val region: String,
    val comuna: String,
    val indicaciones: String,
    val items: List<OrdenItem>
)