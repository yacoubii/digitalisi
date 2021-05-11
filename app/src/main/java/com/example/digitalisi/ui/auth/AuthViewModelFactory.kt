package com.example.digitalisi.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalisi.repository.LoginRepository
import com.example.digitalisi.repository.UserIdRepository

class AuthViewModelFactory (private val loginRepository: LoginRepository, private val userIdRepository: UserIdRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(loginRepository, userIdRepository) as T
    }

}