package com.example.digitalisi.ui.processes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalisi.model.Category
import com.example.digitalisi.model.Process
import com.example.digitalisi.repository.ProcessRepository
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Query

class ProcessViewModel(private val processRepository: ProcessRepository) : ViewModel() {
    var processResponse: MutableLiveData<Response<List<Process>>> = MutableLiveData()

    fun getProcesses(
            @Header("Cookie") jsessionidAndToken : String,
            @Query("p")  p : Int,
            @Query("c")  c : Int,
            @Query("f")  f1 : String,
            @Query("f")  f2 : String,
            @Query("f")  f3 : String
    ){
        viewModelScope.launch{
            val response = processRepository.getProcess(jsessionidAndToken,p,c,f1,f2,f3)
            processResponse.value = response
        }
    }
}