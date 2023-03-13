package com.robosoft.virtuallearnproject.network.loginregister

import com.robosoft.virtuallearnproject.dataclass.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface
RestApi {
    @Headers("Content-Type: application/json")
    @POST("/signIn")
    fun login(@Body data : LoginRequestData) : Call<LoginResponseData>

    @Headers("Content-Type: application/json")
    @POST("/signup")
    fun signUp(@Body data : MobileNumberData) : Call<ResponseMessage>

    @Headers("Content-Type: application/json")
    @POST("/verify")
    fun verify(@Body data : VarifyRequestData) : Call<ResponseMessage>

    @Headers("Content-Type: application/json")
    @POST("/register")
    fun register(@Body data : PersonalDetailsRegisterRequestClass) : Call<RegisterResponseData>

    @Headers("Content-Type: application/json")
    @POST("/resend")
    fun resend(@Body data : MobileNumberData) : Call<ResponseMessage>

    @Headers("Content-Type: application/json")
    @POST("/forgotPassword")
    fun forgotPassword(@Body data : MobileNumberData) : Call<ResponseMessage>

    @Headers("Content-Type: application/json")
    @POST("/forgotPassword/createNewPassword")
    fun createNewPasswrod(@Body data : CreateNewPasswordRequest) : Call<ResponseMessage>

    @Headers("Content-Type: application/json")
    @POST("/changePassword")
    fun updateNewPasswrod(@Header("authorization") access_token : String, @Body data : UpdatePasswordRequest) : Call<ResponseMessage>
}