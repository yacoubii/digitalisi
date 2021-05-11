package com.example.digitalisi.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalisi.model.UserIdResponse
import com.example.digitalisi.repository.LoginRepository
import com.example.digitalisi.repository.UserIdRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Header

class AuthViewModel(private val loginRepository: LoginRepository, private val userIdRepository: UserIdRepository) : ViewModel() {
    var myResponse: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    var userIdResponse: MutableLiveData<Response<UserIdResponse>> = MutableLiveData()

    fun login(username: String, password: String, redirect: Boolean){
        viewModelScope.launch{
            val response = loginRepository.login(username,password,redirect)
            myResponse.value = response
        }
    }

    fun getUserId(@Header("Cookie") jsessionidAndToken : String){
        viewModelScope.launch{
            val response = userIdRepository.getUserId(jsessionidAndToken)
            userIdResponse.value = response
        }
    }

}