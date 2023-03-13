package com.robosoft.virtuallearnproject.dataclass.filters

data class ApplyFilterResponseItem(
    val category: String?,
    val courseId: String?,
    val image: String?,
    val title: String?,
    val totalChapters: Int?,
    val totalLessons: Int?
)