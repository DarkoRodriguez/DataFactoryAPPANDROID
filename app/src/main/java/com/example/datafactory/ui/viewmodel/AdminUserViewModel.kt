package com.example.datafactory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datafactory.data.model.Usuario
import com.example.datafactory.data.remote.RetrofitClient
import com.example.datafactory.data.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminUserViewModel : ViewModel() {

    private val usuarioRepository = UsuarioRepository(RetrofitClient.instance)

    private val _users = MutableStateFlow<List<Usuario>>(emptyList())
    val users: StateFlow<List<Usuario>> = _users

    private val _selectedUser = MutableStateFlow<Usuario?>(null)
    val selectedUser: StateFlow<Usuario?> = _selectedUser

    fun getUsers() {
        viewModelScope.launch {
            val userList = usuarioRepository.getUsuarios()
            if (userList != null) {
                _users.value = userList
            }
        }
    }

    fun getUser(userId: Long) {
        viewModelScope.launch {
            _selectedUser.value = usuarioRepository.getUsuario(userId)
        }
    }
}