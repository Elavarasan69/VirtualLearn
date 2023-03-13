package com.robosoft.virtuallearnproject.dataclass.course

data class CourseResonse(
    val courseOverview: CourseOverview?,
    val isEnrolled: IsEnrolled?,
    val listOfChapters: ListOfChapters?
)