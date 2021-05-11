package com.example.digitalisi.ui.contract

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalisi.repository.ContractRepository

class ContractViewModelFactory(private val contractRepository: ContractRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContractViewModel(contractRepository) as T
    }
}