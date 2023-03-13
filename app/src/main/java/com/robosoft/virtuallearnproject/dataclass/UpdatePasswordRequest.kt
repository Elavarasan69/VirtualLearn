package com.robosoft.virtuallearnproject.dataclass

data class UpdatePasswordRequest(
    val currentPassword: String,
    val newPassword: String
)