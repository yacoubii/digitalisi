package com.example.digitalisi.repository

import com.example.digitalisi.api.RetrofitInstance
import com.example.digitalisi.model.Process
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Query

class ProcessRepository {

    suspend fun getProcess(
            @Header("Cookie") jsessionidAndToken : String,
            @Query("p")  p : Int,
            @Query("c")  c : Int,
            @Query("f")  f1 : String,
            @Query("f")  f2 : String,
            @Query("f")  f3 : String
    ): Response<List<Process>> {
        return RetrofitInstance.api.getProcesses(jsessionidAndToken,p,c,f1,f2,f3)
    }
}