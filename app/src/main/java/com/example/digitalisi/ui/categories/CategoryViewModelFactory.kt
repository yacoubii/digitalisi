package com.example.digitalisi.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalisi.repository.CategoryRepository

class CategoryViewModelFactory(private val categoryRepository: CategoryRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CategoryViewModel(categoryRepository) as T
    }
}