package com.robosoft.virtuallearnproject.dataclass.search

data class SearchResponseItemItem(
    val _id: String,
    val category: String,
    val courseContent: CourseContent,
    val courseImage: String,
    val courseTitle: String
)