package com.robosoft.virtuallearnproject.dataclass.course

data class ListOfChapters(
    val _id: String?,
    val totalChapters: List<TotalChapter?>?,
    val courseContent : CourseContent
)