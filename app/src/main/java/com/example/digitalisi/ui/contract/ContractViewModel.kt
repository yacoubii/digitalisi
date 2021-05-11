package com.example.digitalisi.ui.contract

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalisi.model.Contract
import com.example.digitalisi.repository.ContractRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Path

class ContractViewModel(private val contractRepository:ContractRepository): ViewModel() {
    var contractResponse: MutableLiveData<Response<Contract>> = MutableLiveData()
    fun getContract(
            @Header("Cookie") jsessionidAndToken : String,
            @Path("processId") processId : String
    ){
        viewModelScope.launch {
            val response = contractRepository.getContract(jsessionidAndToken,processId)
            contractResponse.value = response
        }
    }
}