package com.robosoft.virtuallearnproject.dataclass.home.topcourse

data class TopCourseResponseItem(
    val _id: String?,
    val category: String?,
    val courseContent: CourseContent?,
    val courseImage: String?,
    val courseTitle: String?,
    val numberOfEnrollments: Int?
)