package com.robosoft.virtuallearnproject.dataclass

import com.google.gson.annotations.SerializedName

data class RegisterResponseData(
    @SerializedName("message") val message: List<String>,
    @SerializedName("access_Token") val accessToken : String?
)
