
package com.robosoft.virtuallearnproject.network.search

import android.util.Log
import com.robosoft.virtuallearnproject.dataclass.search.SearchRequest
import com.robosoft.virtuallearnproject.dataclass.search.SearchResponseItemItem
import com.robosoft.virtuallearnproject.network.loginregister.ServiceBuilderObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchApiService {

    val searchApiService = ServiceBuilderObject.buildService(SearchApi::class.java)

    suspend fun searchCourse(
        accessToken: String,
        data: SearchRequest,
        onResult: (SearchResponseItemItem?) -> Unit
    ) {
        searchApiService.searchCourse(accessToken, data)
            .enqueue(object : Callback<SearchResponseItemItem> {
                override fun onResponse(
                    call: Call<SearchResponseItemItem>,
                    response: Response<SearchResponseItemItem>
                ) {
                    Log.d("response", response.toString())
                    onResult(response.body())
                }

                override fun onFailure(call: Call<SearchResponseItemItem>, t: Throwable) {
                    Log.d("t", t.message.toString())

                    onResult(null)
                }
            })
    }
}