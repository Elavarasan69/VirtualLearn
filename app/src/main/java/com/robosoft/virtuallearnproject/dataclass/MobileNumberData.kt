package com.robosoft.virtuallearnproject.dataclass

import com.google.gson.annotations.SerializedName

data class MobileNumberData(
    @SerializedName("mobileNumber") val mobile : Long?

)
