package com.robosoft.virtuallearnproject.dataclass

import com.google.gson.annotations.SerializedName

class LoginRequestData (
    @SerializedName("userName") val userName : String?,
    @SerializedName("password") val password : String?,
        )