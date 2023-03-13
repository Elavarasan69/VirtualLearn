package com.robosoft.virtuallearnproject.network.loginregister

import android.util.Log
import com.robosoft.virtuallearnproject.dataclass.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestApiService {
    val retrofit  = ServiceBuilderObject.buildService(RestApi::class.java)

    fun login (data : LoginRequestData, onResult : (LoginResponseData?) -> Unit){
        retrofit.login(data).enqueue(object : Callback<LoginResponseData> {
            override fun onResponse(
                call: Call<LoginResponseData>,
                response: Response<LoginResponseData>
            ) {
                Log.d("response",response.toString())
                val userData = response.body()
                onResult(userData!!)
            }

            override fun onFailure(call: Call<LoginResponseData>, t: Throwable) {
                onResult(null)
            }

        })
    }


    fun signup(data : MobileNumberData, onResult: (ResponseMessage?) -> Unit){
        retrofit.signUp(data).enqueue(object:Callback<ResponseMessage>{
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                Log.d("resp", response.toString())
                onResult(response.body())
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
               onResult(null)
            }

        })
    }

    fun verify(data : VarifyRequestData, onResult: (ResponseMessage?) -> Unit){
        retrofit.verify(data).enqueue(object : Callback<ResponseMessage>{
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
               onResult(null)
            }

        })
    }

    fun register(data: PersonalDetailsRegisterRequestClass, onResult: (RegisterResponseData?) -> Unit){
        retrofit.register(data).enqueue(object : Callback<RegisterResponseData>{
            override fun onResponse(
                call: Call<RegisterResponseData>,
                response: Response<RegisterResponseData>
            ) {
                Log.d("responseMessage", response.toString())
                onResult(response.body())
            }

            override fun onFailure(call: Call<RegisterResponseData>, t: Throwable) {
                onResult(null)
            }

        })
    }

    fun resend(data:MobileNumberData ,  onResult: (ResponseMessage?) -> Unit){
        retrofit.resend(data).enqueue(object: Callback<ResponseMessage>{
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                onResult(null)
            }

        })
    }

    fun forgotPassword(data:MobileNumberData ,  onResult: (ResponseMessage?) -> Unit){
        retrofit.forgotPassword(data).enqueue(object: Callback<ResponseMessage>{
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun createNewPassword(data : CreateNewPasswordRequest, onResult: (ResponseMessage?) -> Unit){
        retrofit.createNewPasswrod(data).enqueue(object : Callback<ResponseMessage>{
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                Log.d("message",response.toString())
               onResult(response.body())
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
               onResult(null)
            }
        })
    }

    fun updateNewPassword(accessToken: String, data : UpdatePasswordRequest, onResult: (ResponseMessage?) -> Unit){
        retrofit.updateNewPasswrod(accessToken, data).enqueue(object : Callback<ResponseMessage>{
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                Log.d("message",response.toString())
                onResult(response.body())
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                onResult(null)
            }
        })
    }

}