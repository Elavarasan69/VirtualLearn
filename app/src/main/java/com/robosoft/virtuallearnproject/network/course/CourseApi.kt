package com.robosoft.virtuallearnproject.network.course

import com.robosoft.virtuallearnproject.dataclass.ResponseMessage
import com.robosoft.virtuallearnproject.dataclass.course.CourseRequest
import com.robosoft.virtuallearnproject.dataclass.course.CourseResonse
import com.robosoft.virtuallearnproject.dataclass.course.EnrollRequest
import com.robosoft.virtuallearnproject.dataclass.getprogress.GetProgressRequest
import com.robosoft.virtuallearnproject.dataclass.getprogress.GetProgressResponse
import com.robosoft.virtuallearnproject.dataclass.updateProgress.UpdateProgressRequest
import com.robosoft.virtuallearnproject.dataclass.videodata.VideoDataRequest
import com.robosoft.virtuallearnproject.dataclass.videodata.VideoDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface CourseApi {

    @Headers("Content-Type: application/json")
    @POST("/getCourse/chapters")
    fun getCourseDetail(@Header("authorization") access_token: String, @Body data : CourseRequest) : Call<CourseResonse>

    @Headers("Content-Type: application/json")
    @POST("/getCourse/overview")
    fun getCourseOverview(@Header("authorization") access_token: String, @Body data : CourseRequest) : Call<CourseResonse>

    @Headers("Content-Type: application/json")
    @POST("/enrollNow")
    fun enrollToCourse(@Header("authorization") access_token: String, @Body data : EnrollRequest) : Call<ResponseMessage>

    @Headers("Content-Type: application/json")
    @POST("/getProgress")
    fun getProgress(@Header("authorization") access_token: String, @Body data: GetProgressRequest) : Call<GetProgressResponse>

    @Headers("Content-Type: application/json")
    @POST("/getVideoData")
    fun getVideoData(@Header("authorization") access_token: String, @Body data: VideoDataRequest) : Call<VideoDataResponse>

    @Headers("Content-Type: application/json")
    @POST("/updateProgress")
    fun updateProgress(@Header("authorization") access_token: String, @Body data: UpdateProgressRequest) : Call<ResponseMessage>

}