package com.robosoft.virtuallearnproject.dataclass

data class ChoiceYourCourseResponseDataClassItem(
    val _id: String,
    val category: String,
    val courseContent: CourseContent,
    val courseImage: String,
    val courseTitle: String,
    val introVideo: String
)