package com.robosoft.virtuallearnproject.dataclass

import com.google.gson.annotations.SerializedName

data class LoginResponseData(
    @SerializedName("access_token") val accessToken : String,
    @SerializedName("message") val message : String
)
