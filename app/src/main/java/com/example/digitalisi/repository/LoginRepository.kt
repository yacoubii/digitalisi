package com.example.digitalisi.repository

import com.example.digitalisi.api.RetrofitInstance
import com.example.digitalisi.model.User
import okhttp3.Cookie
import okhttp3.ResponseBody
import retrofit2.Response

class LoginRepository {
    suspend fun login(username: String, password: String, redirect: Boolean): Response<ResponseBody> {
        return RetrofitInstance.api.login(username,password,redirect)
    }
}