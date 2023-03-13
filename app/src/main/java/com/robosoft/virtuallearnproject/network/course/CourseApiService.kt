
package com.robosoft.virtuallearnproject.network.course

import android.util.Log
import com.robosoft.virtuallearnproject.dataclass.ResponseMessage
import com.robosoft.virtuallearnproject.dataclass.course.CourseRequest
import com.robosoft.virtuallearnproject.dataclass.course.CourseResonse
import com.robosoft.virtuallearnproject.dataclass.course.EnrollRequest
import com.robosoft.virtuallearnproject.dataclass.getprogress.GetProgressRequest
import com.robosoft.virtuallearnproject.dataclass.getprogress.GetProgressResponse
import com.robosoft.virtuallearnproject.dataclass.updateProgress.UpdateProgressRequest
import com.robosoft.virtuallearnproject.dataclass.videodata.VideoDataRequest
import com.robosoft.virtuallearnproject.dataclass.videodata.VideoDataResponse
import com.robosoft.virtuallearnproject.network.loginregister.ServiceBuilderObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseApiService {

    val retrofitObject = ServiceBuilderObject.buildService(CourseApi::class.java)

    fun getCourseDetails(accessToken : String, data: CourseRequest, onResult: (CourseResonse?)->Unit){
        retrofitObject.getCourseDetail(accessToken, data).enqueue(object : Callback<CourseResonse>{
            override fun onResponse(call: Call<CourseResonse>, response: Response<CourseResonse>) {
                Log.d("res", response.toString())
               onResult(response.body())
            }

            override fun onFailure(call: Call<CourseResonse>, t: Throwable) {
               onResult(null)
            }

        })
    }

    fun getCourseOverview(accessToken : String, data: CourseRequest, onResult: (CourseResonse?)->Unit){
        retrofitObject.getCourseOverview(accessToken, data).enqueue(object : Callback<CourseResonse>{
            override fun onResponse(call: Call<CourseResonse>, response: Response<CourseResonse>) {
                Log.d("res", response.body().toString())
                onResult(response.body())
            }

            override fun onFailure(call: Call<CourseResonse>, t: Throwable) {
                onResult(null)
            }

        })
    }

    fun enrollCourse(accessToken: String, data : EnrollRequest, onResult: (ResponseMessage?) -> Unit){
        retrofitObject.enrollToCourse(accessToken,data).enqueue(object :Callback<ResponseMessage>{
            override fun onResponse(call: Call<ResponseMessage>, response: Response<ResponseMessage>) {
                Log.d("enroll", response.toString())
                Log.d("enroll", response.body().toString())
                onResult(response.body())
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                onResult(null)
            }
        })
    }

    fun getProgress(accessToken: String, data:GetProgressRequest, onResult: (GetProgressResponse?)->Unit){
        retrofitObject.getProgress(accessToken,data).enqueue(object:Callback<GetProgressResponse>{
            override fun onResponse(
                call: Call<GetProgressResponse>,
                response: Response<GetProgressResponse>
            ) {
               onResult(response.body())
            }

            override fun onFailure(call: Call<GetProgressResponse>, t: Throwable) {
               onResult(null)
            }

        })
    }

    fun updateProgress(accessToken: String, data:UpdateProgressRequest, onResult: (ResponseMessage?)->Unit){
        retrofitObject.updateProgress(accessToken,data).enqueue(object:Callback<ResponseMessage>{
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
    fun getVideoData(accessToken: String, data:VideoDataRequest, onResult: (VideoDataResponse?)-> Unit){
        retrofitObject.getVideoData(accessToken,data).enqueue(object:Callback<VideoDataResponse>{
            override fun onResponse(
                call: Call<VideoDataResponse>,
                response: Response<VideoDataResponse>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<VideoDataResponse>, t: Throwable) {
                onResult(null)
            }

        })

    }

}