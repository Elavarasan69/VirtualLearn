package com.robosoft.virtuallearnproject.dataclass

import com.google.gson.annotations.SerializedName

data class VarifyRequestData(
    @SerializedName("mobileNumber") val mobileNumber: Long,
    @SerializedName("otpCode") val otpCode: String

)