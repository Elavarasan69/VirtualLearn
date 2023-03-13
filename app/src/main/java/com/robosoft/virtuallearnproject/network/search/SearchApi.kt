package com.robosoft.virtuallearnproject.network.search


import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseRequest
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseResponse
import com.robosoft.virtuallearnproject.dataclass.search.SearchRequest
import com.robosoft.virtuallearnproject.dataclass.search.SearchResponseItem
import com.robosoft.virtuallearnproject.dataclass.search.SearchResponseItemItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface SearchApi {

    @Headers("Content-Type: application/json")
    @POST("/searchCourse")
    fun searchCourse(
        @Header("authorization") access_token: String,
        @Body data: SearchRequest
    ): Call<SearchResponseItemItem>

}