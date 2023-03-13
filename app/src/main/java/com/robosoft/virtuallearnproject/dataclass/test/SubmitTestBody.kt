package com.robosoft.virtuallearnproject.dataclass.test

data class SubmitTestBody(
    val answers: List<Int>,
    val chapterId: String,
    val chapterNumber: Int,
    val chapterTitle: String,
    val courseId: String,
    val testId: String
)