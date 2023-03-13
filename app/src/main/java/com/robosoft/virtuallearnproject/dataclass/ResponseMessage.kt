package com.robosoft.virtuallearnproject.dataclass

import com.google.gson.annotations.SerializedName

data class ResponseMessage(
    @SerializedName("message") val message: String
)