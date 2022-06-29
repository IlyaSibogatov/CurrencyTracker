package com.example.myapplication.api

import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("v2/rates")
    fun getCurrenciesList(): Call<ListCurrency>

    companion object {
        const val BASE_URL = "https://api.coincap.io/"
    }
}