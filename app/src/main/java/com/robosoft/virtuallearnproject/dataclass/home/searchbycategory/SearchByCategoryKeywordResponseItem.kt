package com.robosoft.virtuallearnproject.dataclass.home.searchbycategory

data class SearchByCategoryKeywordResponseItem(
    val _id: String?,
    val category: String?,
    val courseContent: CourseContent?,
    val courseImage: String?,
    val courseTitle: String?,
    val keywords: String?,
    val subCategory: String?
)