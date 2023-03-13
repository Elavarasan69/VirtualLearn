package com.robosoft.virtuallearnproject.dataclass

data class PersonalDetailsRegisterRequestClass(
    val email: String,
    val fullName: String,
    val mobileNumber: Long,
    val password: String,
    val userName: String
)