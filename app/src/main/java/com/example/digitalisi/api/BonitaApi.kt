package com.example.digitalisi.api

import com.example.digitalisi.model.*
import okhttp3.Cookie
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface BonitaApi {
    @FormUrlEncoded
    @POST("/bonita/loginservice")
    suspend fun login(
        @Field("username") username: String?,
        @Field("password") password: String?,
        @Field("redirect") redirect: Boolean

    ):Response<ResponseBody>

    @GET("/bonita/API/system/session/unusedId")
    suspend fun getUserId(@Header("Cookie") jsessionidAndToken : String):Response<UserIdResponse>

    @GET("/bonita/portal/custom-page/API/bpm/category")
    suspend fun getCategories(@Header("Cookie") jsessionidAndToken : String,
                              @Query("p")  p : Int,
                              @Query("f")  f : String):Response<List<Category>>

    @GET("/bonita/API/bpm/process")
    suspend fun getProcesses(
            @Header("Cookie") jsessionidAndToken : String,
            @Query("p")  p : Int,
            @Query("c")  c : Int,
            @Query("f")  f1 : String,
            @Query("f")  f2 : String,
            @Query("f")  f3 : String
    ):Response<List<Process>>

    @GET("/bonita/API/bpm/process/{processId}/contract")
    suspend fun getContract(
            @Header("Cookie") jsessionidAndToken : String,
            @Path("processId") processId : String
    ):Response<Contract>

    @Headers("Content-Type: application/json")
    @POST("/bonita/API/bpm/process/{processId}/instantiation")
    suspend fun submitForm(
            @Header("Cookie") jsessionidAndToken : String,
            @Header("X-Bonita-API-Token") token : String,
            @Path("processId") processId : String,
            @Body form : String
    ):Response<Case>
}