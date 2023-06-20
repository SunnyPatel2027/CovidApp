package com.example.task.model

//data class Timeline(
//    val cases: Cases,
//    val deaths: Deaths,
//    val recovered: Recovered
//)

data class Timeline(
    val cases: Map<String, Int>,
    val deaths: Map<String, Int>,
    val recovered: Map<String, Int>
)