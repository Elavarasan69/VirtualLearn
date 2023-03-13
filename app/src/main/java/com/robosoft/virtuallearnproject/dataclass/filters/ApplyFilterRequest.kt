package com.robosoft.virtuallearnproject.dataclass.filters

data class ApplyFilterRequest(
    val categories: MutableList<String?>?,
    val totalDuration: MutableList<List<Int?>?>?
)