package com.robosoft.virtuallearnproject.dataclass.home.searchbysubcategory

data class SearchBySubCategoryResponseItem(
    val _id: String,
    val category: String,
    val courseContent: CourseContent,
    val courseImage: String,
    val courseTitle: String,
    val subCategory: String
)