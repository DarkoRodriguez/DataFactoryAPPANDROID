package com.example.datafactory.data.local

import com.example.datafactory.data.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object SessionManager {
    private val _currentUser = MutableStateFlow<Usuario?>(null)
    val currentUser = _currentUser.asStateFlow()

    val userRole: String?
        get() = _currentUser.value?.indicaciones

    fun login(user: Usuario) {
        _currentUser.value = user
    }

    fun logout() {
        _currentUser.value = null
    }
}