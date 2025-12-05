package com.example.datafactory.data.remote

import com.example.datafactory.data.model.CarritoItem
import com.example.datafactory.data.model.Orden
import com.example.datafactory.data.model.Producto
import com.example.datafactory.data.model.Usuario
import com.example.datafactory.data.model.OrdenItem
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // Usuarios
    @GET("api/v1/usuarios")
    suspend fun getUsuarios(): Response<List<Usuario>>

    @GET("api/v1/usuarios/{id}")
    suspend fun getUsuario(@Path("id") id: Long): Response<Usuario>

    @POST("api/v1/usuarios")
    suspend fun createUsuario(@Body usuario: Usuario): Response<Usuario>

    @PUT("api/v1/usuarios/{id}")
    suspend fun updateUsuario(@Path("id") id: Long, @Body usuario: Usuario): Response<Usuario>

    @DELETE("api/v1/usuarios/{id}")
    suspend fun deleteUsuario(@Path("id") id: Long): Response<Unit>

    // Productos
    @GET("api/v1/producto")
    suspend fun getProductos(): Response<List<Producto>>

    @GET("api/v1/producto/{id}")
    suspend fun getProducto(@Path("id") id: Long): Response<Producto>

    @POST("api/v1/producto")
    suspend fun createProducto(@Body producto: Producto): Response<Producto>

    @PUT("api/v1/producto/{id}")
    suspend fun updateProducto(@Path("id") id: Long, @Body producto: Producto): Response<Producto>

    @DELETE("api/v1/producto/{id}")
    suspend fun deleteProducto(@Path("id") id: Long): Response<Unit>

    // Orden
    @GET("api/v1/orden")
    suspend fun getOrdenes(): Response<List<Orden>>

    @GET("api/v1/orden/{id}")
    suspend fun getOrden(@Path("id") id: Long): Response<Orden>

    @POST("api/v1/orden")
    suspend fun createOrden(@Body orden: Orden): Response<Orden>

    @PUT("api/v1/orden/{id}")
    suspend fun updateOrden(@Path("id") id: Long, @Body orden: Orden): Response<Orden>

    @DELETE("api/v1/orden/{id}")
    suspend fun deleteOrden(@Path("id") id: Long): Response<Unit>
    
    // OrdenItem
    @GET("api/v1/orden_item")
    suspend fun getOrdenItems(): Response<List<OrdenItem>>

    @GET("api/v1/orden_item/{id}")
    suspend fun getOrdenItem(@Path("id") id: Long): Response<OrdenItem>

    @POST("api/v1/orden_item")
    suspend fun createOrdenItem(@Body ordenItem: OrdenItem): Response<OrdenItem>

    @PUT("api/v1/orden_item/{id}")
    suspend fun updateOrdenItem(@Path("id") id: Long, @Body ordenItem: OrdenItem): Response<OrdenItem>

    @DELETE("api/v1/orden_item/{id}")
    suspend fun deleteOrdenItem(@Path("id") id: Long): Response<Unit>

    // CarritoItem
    @GET("api/v1/carrito_item")
    suspend fun getCarritoItems(): Response<List<CarritoItem>>

    @GET("api/v1/carrito_item/{id}")
    suspend fun getCarritoItem(@Path("id") id: Long): Response<CarritoItem>

    @POST("api/v1/carrito_item")
    suspend fun createCarritoItem(@Body carritoItem: CarritoItem): Response<CarritoItem>

    @PUT("api/v1/carrito_item/{id}")
    suspend fun updateCarritoItem(@Path("id") id: Long, @Body carritoItem: CarritoItem): Response<CarritoItem>

    @DELETE("api/v1/carrito_item/{id}")
    suspend fun deleteCarritoItem(@Path("id") id: Long): Response<Unit>
}