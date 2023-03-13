package com.robosoft.virtuallearnproject.network.mycourse

import com.robosoft.virtuallearnproject.dataclass.MyCourse.CompletedData
import com.robosoft.virtuallearnproject.dataclass.MyCourse.OngoingResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface MyCourseApi {

    @Headers("Content-Type: application/json")
    @GET("/ongoingCourses")
    fun onGoing(@Header("authorization") access_token: String) : Call<OngoingResponseData>

    @Headers("Content-Type: application/json")
    @GET("/completedCourses")
    fun completed(@Header("authorization") access_token: String) : Call<CompletedData>
}