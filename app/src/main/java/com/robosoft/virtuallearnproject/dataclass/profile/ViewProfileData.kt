package com.robosoft.virtuallearnproject.dataclass.profile

data class ViewProfileData(
    val chapters: Int,
    val courses: Int,
    val email: String,
    val mobileNumber: Long,
    val name: String,
    val profileImage: String,
    val test: Int,
    val username: String,
    val occupation: String?,
    val dateOfBirth: String?,
    val twitterLink: String?,
    val facebook: String?,
    val gender: String?
)