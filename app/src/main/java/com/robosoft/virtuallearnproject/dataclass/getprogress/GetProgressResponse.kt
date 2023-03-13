package com.robosoft.virtuallearnproject.dataclass.getprogress

data class GetProgressResponse(
    val allVideoStatus: List<Any?>?,
    val approvalRate: String?,
    val chaptersCompleted: Int?,
    val courseId: String?,
    val courseStatus: String?,
    val enrolledOn: String?,
    val lessonsCompleted: Int?,
    val ongoingChapter: Int?,
    val ongoingSerialNumber: Int?,
    val ongoingVideo: OngoingVideo?,
    val testApprovalRate: List<Int?>?,
    val testCompleted: List<Any?>?,
    val testPassed: List<Int?>?,
    val testsPassed: Int?,
    val totalScreenTime: String?,
    val userId: String?,
    val message: String?
)