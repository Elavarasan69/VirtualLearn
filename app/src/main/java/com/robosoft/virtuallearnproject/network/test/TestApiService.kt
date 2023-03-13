package com.robosoft.virtuallearnproject.network.test

import android.util.Log
import com.robosoft.virtuallearnproject.dataclass.ResponseMessage
import com.robosoft.virtuallearnproject.dataclass.test.*
import com.robosoft.virtuallearnproject.network.loginregister.ServiceBuilderObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestApiService {

    val retrofit = ServiceBuilderObject.buildService(TestApi::class.java)

    fun displayTest(
        accessToken: String,
        data: TestBodyData,
        onResult: (TestResponseData?) -> Unit
    ) {
        retrofit.displayTest(accessToken, data).enqueue(object : Callback<TestResponseData> {
            override fun onResponse(
                call: Call<TestResponseData>,
                response: Response<TestResponseData>
            ) {
                Log.d("DisplayTest", response.toString())
                onResult(response.body())
            }

            override fun onFailure(call: Call<TestResponseData>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun submitTest(
        accessToken: String,
        data: SubmitTestBody,
        onResult: (ResponseMessage?) -> Unit
    ) {
        retrofit.submitTest(accessToken, data).enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                Log.d("Submit Test", response.toString())
                onResult(response.body())
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun testResult(
        accessToken: String,
        data: TestResultBody,
        onResult: (TestResultResponse?) -> Unit
    ) {
        retrofit.testResult(accessToken, data).enqueue(object : Callback<TestResultResponse> {
            override fun onResponse(
                call: Call<TestResultResponse>,
                response: Response<TestResultResponse>
            ) {
                Log.d("Submit Test", response.toString())
                onResult(response.body())
            }
            override fun onFailure(call: Call<TestResultResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun getTestStatus(
        accessToken: String,
        data: TestStatusBody,
        onResult: (ResponseMessage?) -> Unit
    ) {
        retrofit.getTestStatus(accessToken, data).enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                Log.d("test status", response.toString())
                onResult(response.body())
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                onResult(null)
            }
        })
    }
}