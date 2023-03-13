package com.robosoft.virtuallearnproject.network.myprofile

import com.robosoft.virtuallearnproject.dataclass.ResponseMessage
import com.robosoft.virtuallearnproject.dataclass.profile.EditProfileDataX
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface EditProfileApi {
    @Headers("Content-Type: application/json")
    @POST("/editProfile")
    fun editProfile(@Header("authorization") access_token: String,
                    @Body editData: EditProfileDataX
    ) : Call<ResponseMessage>


   // @Headers("Content-Type: application/json")
    //@POST("/editProfilePhoto")
   // fun editProfilePhoto(@Header("authorization") access_token: String, @Body data : EditProfileData) : Call<ResponseMessage>
}
