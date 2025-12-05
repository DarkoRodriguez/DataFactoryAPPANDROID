package com.example.datafactory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datafactory.data.local.SessionManager
import com.example.datafactory.data.model.Usuario
import com.example.datafactory.data.remote.RetrofitClient
import com.example.datafactory.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null,
    val userRole: String? = null
)

class AuthViewModel : ViewModel() {

    private val usuarioRepository = UsuarioRepository(RetrofitClient.instance)

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            try {
                val usuarios = usuarioRepository.getUsuarios()
                val user = usuarios?.find { it.email == email && it.password == password }
                if (user != null) {
                    SessionManager.login(user) // Save user to session
                    val role = if (user.indicaciones == "admin") "admin" else "client"
                    _authState.value = AuthState(success = true, userRole = role)
                } else {
                    _authState.value = AuthState(error = "Invalid credentials")
                }
            } catch (e: Exception) {
                _authState.value = AuthState(error = e.message)
            }
        }
    }

    fun register(usuario: Usuario) {
        viewModelScope.launch {
            _authState.value = AuthState(isLoading = true)
            try {
                val createdUser = usuarioRepository.createUsuario(usuario)
                if (createdUser != null) {
                    _authState.value = AuthState(success = true)
                } else {
                    _authState.value = AuthState(error = "Registration failed")
                }
            } catch (e: Exception) {
                _authState.value = AuthState(error = e.message)
            }
        }
    }
}