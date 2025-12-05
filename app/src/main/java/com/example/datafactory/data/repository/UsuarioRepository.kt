package com.example.datafactory.data.repository

import com.example.datafactory.data.model.Usuario
import com.example.datafactory.data.remote.ApiService

class UsuarioRepository(private val apiService: ApiService) {

    suspend fun getUsuarios(): List<Usuario>? {
        val response = apiService.getUsuarios()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun getUsuario(id: Long): Usuario? {
        val response = apiService.getUsuario(id)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun createUsuario(usuario: Usuario): Usuario? {
        val response = apiService.createUsuario(usuario)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun updateUsuario(id: Long, usuario: Usuario): Usuario? {
        val response = apiService.updateUsuario(id, usuario)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun deleteUsuario(id: Long): Boolean {
        val response = apiService.deleteUsuario(id)
        return response.isSuccessful
    }
}