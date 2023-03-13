package com.robosoft.virtuallearnproject.network.myprofile

import com.robosoft.virtuallearnproject.dataclass.profile.ViewProfileData
import com.robosoft.virtuallearnproject.network.loginregister.ServiceBuilderObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewProfileApiService {
    val viewProfileApiService = ServiceBuilderObject.buildService(ViewProfileApi::class.java)

    suspend fun viewProfile(accessToken: String, onResult: (ViewProfileData?) -> Unit) {
        viewProfileApiService.viewProfile(accessToken).enqueue(object : Callback<ViewProfileData>{
            override fun onResponse(
                call: Call<ViewProfileData>,
                response: Response<ViewProfileData>
            ) {
               onResult(response.body())
            }

            override fun onFailure(call: Call<ViewProfileData>, t: Throwable) {
               onResult(null)
            }


        })

    }
}