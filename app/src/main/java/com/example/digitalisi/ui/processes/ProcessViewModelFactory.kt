package com.example.digitalisi.ui.processes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalisi.repository.ProcessRepository

class ProcessViewModelFactory(private val processRepository: ProcessRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProcessViewModel(processRepository) as T
    }
}