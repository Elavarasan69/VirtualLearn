package com.robosoft.virtuallearnproject.dataclass

data class CreateNewPasswordRequest(
    val mobileNumber: Long,
    val newPassword: String
)