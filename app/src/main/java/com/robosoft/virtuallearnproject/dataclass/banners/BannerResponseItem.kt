package com.robosoft.virtuallearnproject.dataclass.banners

data class BannerResponseItem(
    val BannerImage: String?,
    val _id: String?,
    val category: String?,
    val courseImage: String?,
    val courseTitle: String?,
    val courseContent: CourseContent?
)