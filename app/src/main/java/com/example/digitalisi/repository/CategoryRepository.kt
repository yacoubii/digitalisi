package com.example.digitalisi.repository

import com.example.digitalisi.api.RetrofitInstance
import com.example.digitalisi.model.Category
import com.example.digitalisi.model.UserIdResponse
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Query

class CategoryRepository {

    suspend fun getCategories(@Header("Cookie") jsessionidAndToken : String,
                              @Query("p")  p : Int,
                              @Query("f")  f : String): Response<List<Category>> {
        return RetrofitInstance.api.getCategories(jsessionidAndToken,p,f)
    }
}