package com.robosoft.virtuallearnproject.network.myprofile

import com.robosoft.virtuallearnproject.dataclass.profile.ViewProfileData
import retrofit2.Call
import retrofit2.http.*

interface ViewProfileApi {
    @Headers("Content-Type: application/json")
    @GET("/profile")
    fun viewProfile(@Header("authorization") access_token: String) : Call<ViewProfileData>

}