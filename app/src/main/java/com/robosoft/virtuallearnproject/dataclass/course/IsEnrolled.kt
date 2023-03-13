package com.robosoft.virtuallearnproject.dataclass.course

data class IsEnrolled(
    val approvalRate : String?,
    val chaptersCompleted: Int?,
    val courseId: String?,
    val courseStatus: String?,
    val lessonsCompleted: Int?,
    val ongoingChapter: Int?,
    val ongoingVideo: OngoingVideo?,
    val testCompleted: List<Any?>?,
    val testPassed: List<Any?>?,
    val testApprovalRate: List<Any?>?,
    val ongoingSerialNumber : Int?,
    val totalScreenTime: String?,
    val testsPassed: Int?,
   val allVideoStatus: List<Any?>?
)