package com.robosoft.virtuallearnproject.network.home

import android.util.Log
import com.robosoft.virtuallearnproject.dataclass.banners.BannerResponse
import com.robosoft.virtuallearnproject.dataclass.filters.ApplyFilterRequest
import com.robosoft.virtuallearnproject.dataclass.filters.ApplyFilterResponse
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
import com.robosoft.virtuallearnproject.network.loginregister.ServiceBuilderObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeApiService {
    val homeApiService = ServiceBuilderObject.buildService(HomeApi::class.java)

    suspend fun choiceCourse(
        accessToken: String,
        data: ChoiceCourseRequest,
        onResult: (ChoiceCourseResponse?) -> Unit
    ) {
        homeApiService.choiceYourCourse(accessToken, data)
            .enqueue(object : Callback<ChoiceCourseResponse> {
                override fun onResponse(
                    call: Call<ChoiceCourseResponse>,
                    response: Response<ChoiceCourseResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<ChoiceCourseResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    suspend fun topCourseInCategory(
        accessToken: String,
        data: TopCourseRequest,
        onResult: (TopCourseResponse?) -> Unit
    ) {
        homeApiService.topCourseInCategory(accessToken, data)
            .enqueue(object : Callback<TopCourseResponse> {
                override fun onResponse(
                    call: Call<TopCourseResponse>,
                    response: Response<TopCourseResponse>
                ) {
                    Log.d("topCourse", response.toString())
                    onResult(response.body())
                }

                override fun onFailure(call: Call<TopCourseResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    suspend fun searchbyCategoryKeyword(
        accessToken: String,
        data: SearchByCategoryAndKeywordsRequest,
        onResult: (SearchByCategoryKeywordResponse?) -> Unit
    ) {
        homeApiService.searchByCategoryAndKeywords(accessToken, data)
            .enqueue(object : Callback<SearchByCategoryKeywordResponse> {
                override fun onResponse(
                    call: Call<SearchByCategoryKeywordResponse>,
                    response: Response<SearchByCategoryKeywordResponse>
                ) {

                    Log.d("searchByCategoryKeyword", response.toString())
                    onResult(response.body())

                }

                override fun onFailure(call: Call<SearchByCategoryKeywordResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    suspend fun subCategories(
        accessToken: String,
        data: SubCategorieRequest,
        onResult: (SubCtegoriesResponseData?) -> Unit
    ) {
        homeApiService.getSubCategories(accessToken, data)
            .enqueue(object : Callback<SubCtegoriesResponseData> {
                override fun onResponse(
                    call: Call<SubCtegoriesResponseData>,
                    response: Response<SubCtegoriesResponseData>
                ) {

                    Log.d("subcategorie", response.toString())
                    onResult(response.body())

                }

                override fun onFailure(call: Call<SubCtegoriesResponseData>, t: Throwable) {
                    onResult(null)
                }
            })
    }

    fun searchbySubCategory(
        accessToken: String,
        data: SearchBySubCategoryRequest,
        onResult: (SearchBySubCategoryResponse?) -> Unit
    ) {
        homeApiService.searchBySubCategory(accessToken, data)
            .enqueue(object : Callback<SearchBySubCategoryResponse> {
                override fun onResponse(
                    call: Call<SearchBySubCategoryResponse>,
                    response: Response<SearchBySubCategoryResponse>
                ) {

                    Log.d("searchBySubCategory", response.toString())
                    onResult(response.body())

                }

                override fun onFailure(call: Call<SearchBySubCategoryResponse>, t: Throwable) {
                    onResult(null)
                }
            })

    }

    suspend fun getBanners(accessToken: String, onResult: (BannerResponse?) -> Unit) {
        homeApiService.getBanners(accessToken).enqueue(object : Callback<BannerResponse> {
            override fun onResponse(
                call: Call<BannerResponse>,
                response: Response<BannerResponse>
            ) {
                onResult(response.body())
            }

            override fun onFailure(call: Call<BannerResponse>, t: Throwable) {
                onResult(null)
            }
        })
    }

    suspend fun applyFilters(
        accessToken: String,
        data: ApplyFilterRequest,
        onResult: (ApplyFilterResponse?) -> Unit
    ) {
        homeApiService.applyFilter(accessToken, data)
            .enqueue(object : Callback<ApplyFilterResponse> {
                override fun onResponse(
                    call: Call<ApplyFilterResponse>,
                    response: Response<ApplyFilterResponse>
                ) {
                    onResult(response.body())
                }

                override fun onFailure(call: Call<ApplyFilterResponse>, t: Throwable) {
                    onResult(null)
                }
            })
    }

}

