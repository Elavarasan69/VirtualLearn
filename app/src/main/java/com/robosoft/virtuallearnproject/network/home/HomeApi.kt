package com.robosoft.virtuallearnproject.network.home

import com.robosoft.virtuallearnproject.dataclass.banners.BannerResponse
import com.robosoft.virtuallearnproject.dataclass.filters.ApplyFilterRequest
import com.robosoft.virtuallearnproject.dataclass.filters.ApplyFilterResponse
import com.robosoft.virtuallearnproject.dataclass.home.FullNameResponse
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseRequest
import com.robosoft.virtuallearnproject.dataclass.home.choicecourse.ChoiceCourseResponse
import com.robosoft.virtuallearnproject.dataclass.home.searchbycategory.SearchByCategoryAndKeywordsRequest
import com.robosoft.virtuallearnproject.dataclass.home.searchbycategory.SearchByCategoryKeywordResponse
import com.robosoft.virtuallearnproject.dataclass.home.searchbysubcategory.SearchBySubCategoryRequest
import com.robosoft.virtuallearnproject.dataclass.home.searchbysubcategory.SearchBySubCategoryResponse
import com.robosoft.virtuallearnproject.dataclass.home.subcategories.SubCategorieRequest
import com.robosoft.virtuallearnproject.dataclass.home.subcategories.SubCtegoriesResponseData
import com.robosoft.virtuallearnproject.dataclass.home.topcourse.TopCourseRequest
import com.robosoft.virtuallearnproject.dataclass.home.topcourse.TopCourseResponse
import retrofit2.Call
import retrofit2.http.*

interface HomeApi {

    @Headers("Content-Type: application/json")
    @POST("choiceYourCourse")
     fun choiceYourCourse(
        @Header("authorization") access_token: String,
        @Body data: ChoiceCourseRequest
    ): Call<ChoiceCourseResponse>

    @Headers("Content-Type: application/json")
    @POST("/topCoursesInCategory")
     fun topCourseInCategory(@Header("authorization") access_token : String, @Body data: TopCourseRequest) : Call<TopCourseResponse>

    @Headers("Content-Type: application/json")
    @POST("/searchByCategoryAndKeywords")
    fun searchByCategoryAndKeywords(@Header("authorization") access_token : String, @Body data: SearchByCategoryAndKeywordsRequest) : Call<SearchByCategoryKeywordResponse>

    @Headers("Content-Type: application/json")
    @GET("/getName")
    fun getName(@Header("authorization") access_token : String) : Call<FullNameResponse>

    @Headers("Content-Type: application/json")
    @POST("/getSubCategories")
    fun getSubCategories(@Header("authorization") access_token : String, @Body data: SubCategorieRequest) : Call<SubCtegoriesResponseData>


    @Headers("Content-Type: application/json")
    @POST("/searchBySubCategories")
    fun searchBySubCategory(@Header("authorization") access_token: String, @Body data: SearchBySubCategoryRequest) : Call<SearchBySubCategoryResponse>

    @Headers("Content-Type: application/json")
    @GET("/getBanner")
    fun getBanners(@Header("authorization") access_token : String) : Call<BannerResponse>

    @Headers("Content-Type: application/json")
    @POST("/filter")
    fun applyFilter(@Header("authorization") access_token : String, @Body data: ApplyFilterRequest) : Call<ApplyFilterResponse>


}