package com.example.task.model

data class CovidData(
    val country: String,
    val province: List<String>,
    val timeline: Timeline
)

