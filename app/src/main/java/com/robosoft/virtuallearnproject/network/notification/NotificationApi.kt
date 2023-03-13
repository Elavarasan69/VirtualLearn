package com.robosoft.virtuallearnproject.network.notification

import com.robosoft.virtuallearnproject.dataclass.home.FullNameResponse
import com.robosoft.virtuallearnproject.dataclass.notification.NotificationData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface NotificationApi {
    @Headers("Content-Type: application/json")
    @GET("/viewNotifications")
    fun getNotification(@Header("authorization") access_token : String) : Call<NotificationData>
}