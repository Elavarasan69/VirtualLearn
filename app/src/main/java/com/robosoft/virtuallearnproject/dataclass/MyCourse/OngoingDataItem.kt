package com.robosoft.virtuallearnproject.dataclass.MyCourse

data class OngoingDataItem(
    val chaptersCompleted: Int,
    val courseId: String,
    val title: String,
    val totalChapters: Int,
    val url: String?,
    val totalLessons: Int,
    val category : String?
)