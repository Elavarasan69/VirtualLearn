package com.robosoft.virtuallearnproject.dataclass.profile

import com.google.android.material.imageview.ShapeableImageView

data class EditProfileDataX(
    val dateOfBirth: String?,
    val email: String?,
    val facebookLink: String?,
    val fullName: String?,
    val gender: String?,
    val mobileNumber: Long?,
    val occupation: String?,
    val password: String?,
    val twitterLink: String?,
    val userName: String?,
    val profileImageEdit: String?
)