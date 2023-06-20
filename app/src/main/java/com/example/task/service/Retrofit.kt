package com.example.mycric.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun  getClient(baseUrl : String): Retrofit? {
    var retrofit: Retrofit? =  null;
    var baseURL =baseUrl
    if (retrofit == null) {
        retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    } else {
        if (!retrofit.baseUrl().equals(baseURL)) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
    return retrofit
}


