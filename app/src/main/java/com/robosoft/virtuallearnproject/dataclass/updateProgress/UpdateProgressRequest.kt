package com.robosoft.virtuallearnproject.dataclass.updateProgress

data class UpdateProgressRequest(
    val courseId: String?,
    val pauseTime: String?,
    val videoCompleted: String?,
    val videoSerialNumber: Int?
)