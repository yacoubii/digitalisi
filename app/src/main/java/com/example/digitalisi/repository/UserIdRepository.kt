package com.example.digitalisi.repository

import com.example.digitalisi.api.RetrofitInstance
import com.example.digitalisi.model.UserIdResponse
import retrofit2.Response
import retrofit2.http.Header

class UserIdRepository {
    suspend fun getUserId(@Header("Cookie") jsessionidAndToken : String): Response<UserIdResponse> {
     return RetrofitInstance.api.getUserId(jsessionidAndToken)
 }
}