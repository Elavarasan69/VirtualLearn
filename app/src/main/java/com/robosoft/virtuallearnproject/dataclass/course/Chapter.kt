package com.robosoft.virtuallearnproject.dataclass.course

data class Chapter(
    val _id: String?,
    val chapterNumber: Int?,
    val chapterTitle: String?,
    val lessons: List<Lesson?>?,
    val numberOfLessons: Int?,
    val test: Test?
)