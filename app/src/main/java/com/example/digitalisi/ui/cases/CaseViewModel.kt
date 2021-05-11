package com.example.digitalisi.ui.cases

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalisi.model.Case
import com.example.digitalisi.repository.CaseRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

class CaseViewModel(private val caseRepository: CaseRepository):ViewModel() {
    val caseResponse: MutableLiveData<Response<Case>> = MutableLiveData()

    fun submitForm(
            @Header("Cookie") jsessionidAndToken : String,
            @Header("X-Bonita-API-Token") token : String,
            @Path("processId") processId : String,
            @Body form : String
    ){
        viewModelScope.launch {
            val response = caseRepository.submitForm(jsessionidAndToken,token,processId,form)
            caseResponse.value=response
        }
    }
}