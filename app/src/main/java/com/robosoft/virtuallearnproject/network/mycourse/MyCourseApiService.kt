package com.robosoft.virtuallearnproject.network.mycourse

import com.robosoft.virtuallearnproject.dataclass.MyCourse.CompletedData
import com.robosoft.virtuallearnproject.dataclass.MyCourse.OngoingResponseData
import com.robosoft.virtuallearnproject.network.loginregister.ServiceBuilderObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyCourseApiService {

    val retrofit = ServiceBuilderObject.buildService(MyCourseApi::class.java)

    fun onGoing(access_token: String, onResult: (OngoingResponseData?) -> Unit) {

        retrofit.onGoing(access_token).enqueue(object : Callback<OngoingResponseData> {
            override fun onResponse(
                call: Call<OngoingResponseData>,
                response: Response<OngoingResponseData>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<OngoingResponseData>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun completed(access_token: String, onResult: (CompletedData?) -> Unit) {

        retrofit.completed(access_token).enqueue(object : Callback<CompletedData> {
            override fun onResponse(
                call: Call<CompletedData>,
                response: Response<CompletedData>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<CompletedData>, t: Throwable) {
                onResult(null)
            }
        })
    }
}