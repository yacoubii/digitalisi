package com.example.digitalisi.repository

import com.example.digitalisi.api.RetrofitInstance
import com.example.digitalisi.model.Contract
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Path

class ContractRepository {
    suspend fun getContract(
            @Header("Cookie") jsessionidAndToken : String,
            @Path("processId") processId : String
    ):Response<Contract>{
        return RetrofitInstance.api.getContract(jsessionidAndToken,processId)
    }

}