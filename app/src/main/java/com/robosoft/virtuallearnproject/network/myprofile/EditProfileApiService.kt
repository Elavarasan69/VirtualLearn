package com.robosoft.virtuallearnproject.network.myprofile

import android.util.Log
import com.robosoft.virtuallearnproject.dataclass.ResponseMessage
import com.robosoft.virtuallearnproject.dataclass.profile.EditProfileDataX
import com.robosoft.virtuallearnproject.network.loginregister.ServiceBuilderObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileApiService {
    val editProfileApiService = ServiceBuilderObject.buildService(EditProfileApi::class.java)

    fun editProfile(accessToken: String, data: EditProfileDataX, onResult: (ResponseMessage?)->Unit){
     editProfileApiService.editProfile(accessToken, data).enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                Log.d("messag123", response.toString())
               onResult(response.body())
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
             onResult(null)
                 }
        })
    }
}
