package com.robosoft.virtuallearnproject.network.test

import com.robosoft.virtuallearnproject.dataclass.ResponseMessage
import com.robosoft.virtuallearnproject.dataclass.test.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface TestApi {
    @Headers("Content-Type: application/json")
    @POST("/displayTest")
    fun displayTest(@Header("authorization") access_token : String, @Body data: TestBodyData) : Call<TestResponseData>

    @Headers("Content-Type: application/json")
    @POST("/submitTest")
    fun submitTest(@Header("authorization") access_token : String, @Body data: SubmitTestBody) : Call<ResponseMessage>

    @Headers("Content-Type: application/json")
    @POST("/getCompletedTestResultData")
    fun testResult(@Header("authorization") access_token : String, @Body data: TestResultBody) : Call<TestResultResponse>

    @Headers("Content-Type: application/json")
    @POST("/getTestStatus")
    fun getTestStatus(@Header("authorization") access_token: String, @Body data: TestStatusBody) : Call<ResponseMessage>
}