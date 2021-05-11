package com.example.digitalisi.ui.cases

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.digitalisi.repository.CaseRepository

class CaseViewModelFactory(private val caseRepository: CaseRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CaseViewModel(caseRepository) as T
    }
}