package com.example.task.service

import com.example.task.model.AllCountry
import com.example.task.model.AllCountryWise
import com.example.task.model.CricModel
import com.example.task.model.DisModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET(".")
    fun saveUser(): Call<AllCountryWise>
}

interface AllCountryInterface {
    @GET(".")
    fun allCountry(): Call<AllCountry>
}

interface DisCountryApi {
    @GET(".")
    fun disCountry(): Call<DisModel>
}





