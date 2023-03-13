package com.robosoft.virtuallearnproject.network.notification

import com.robosoft.virtuallearnproject.dataclass.notification.NotificationData
import com.robosoft.virtuallearnproject.dataclass.profile.ViewProfileData
import com.robosoft.virtuallearnproject.network.loginregister.ServiceBuilderObject
import com.robosoft.virtuallearnproject.network.myprofile.ViewProfileApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationApiService {
    val notificationApiService = ServiceBuilderObject.buildService(NotificationApi::class.java)

    suspend fun getNotification(accessToken: String, onResult: (NotificationData?) -> Unit) {
        notificationApiService.getNotification(accessToken).enqueue(object : Callback<NotificationData> {
            override fun onResponse(
                call: Call<NotificationData>,
                response: Response<NotificationData>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<NotificationData>, t: Throwable) {
                onResult(null)
            }
        })

    }
}