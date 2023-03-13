package com.robosoft.virtuallearnproject.dataclass.test

data class TestResponseData(
    val _id: String,
    val options: List<List<String>>,
    val questions: List<String>,
    val totalQuestions: Int,
    val totalTimeAlloted: String
)