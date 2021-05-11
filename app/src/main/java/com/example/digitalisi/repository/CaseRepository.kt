package com.example.digitalisi.repository

import com.example.digitalisi.api.RetrofitInstance
import com.example.digitalisi.model.Case
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

class CaseRepository {
    suspend fun submitForm(
            @Header("Cookie") jsessionidAndToken : String,
            @Header("X-Bonita-API-Token") token : String,
            @Path("processId") processId : String,
            @Body form : String
    ):Response<Case>{
        return RetrofitInstance.api.submitForm(jsessionidAndToken,token,processId,form)
    }
}