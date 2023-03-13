package com.robosoft.virtuallearnproject.dataclass.test

data class TestResultResponse(
    val correctlyAnswered: String,
    val correctlyAnsweredInHundred: Int,
    val options: List<List<String>>,
    val passingGrade: String,
    val questions: List<String>,
    val selectedAndActualAnswerSet: List<List<String>>,
    val wronglyAnswered: String
)