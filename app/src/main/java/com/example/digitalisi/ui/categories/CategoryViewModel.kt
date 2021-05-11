package com.example.digitalisi.ui.categories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalisi.model.Category
import com.example.digitalisi.repository.CategoryRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Query

class CategoryViewModel(private val categoryRepository: CategoryRepository): ViewModel() {
    var categoryResponse: MutableLiveData<Response<List<Category>>> = MutableLiveData()

    fun getCategories(@Header("Cookie") jsessionidAndToken : String, @Query("p")  p : Int, @Query("f")  f : String){
        viewModelScope.launch{
            val response = categoryRepository.getCategories(jsessionidAndToken,p,f)
            categoryResponse.value = response
        }
    }
}