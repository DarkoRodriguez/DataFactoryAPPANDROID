package com.example.datafactory.data.model

data class Producto(
    val id: Long? = null,
    val categoria: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: String,
    val oferta: Boolean,
    val descuento: Double,
    val stock: Int
)