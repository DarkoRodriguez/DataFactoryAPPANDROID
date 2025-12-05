package com.example.datafactory.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datafactory.data.remote.RetrofitClient
import com.example.datafactory.data.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val categoryRepository = CategoryRepository(RetrofitClient.instance)

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            val categoryList = categoryRepository.getCategories()
            if (categoryList != null) {
                _categories.value = categoryList
            }
        }
    }

    fun createCategory(category: String) {
        viewModelScope.launch {
            categoryRepository.createCategory(category)
            getCategories() // Refresh the list
        }
    }
}