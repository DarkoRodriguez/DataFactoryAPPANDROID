package com.example.datafactory.data.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    val id: Long? = null, // Made nullable
    val nombre: String,
    val email: String,
    val password: String,
    val telefono: Int,
    val region: String,
    val comuna: String,
    val indicaciones: String,
    @SerializedName("fecha_creacion")
    val fechaCreacion: String? = null
)