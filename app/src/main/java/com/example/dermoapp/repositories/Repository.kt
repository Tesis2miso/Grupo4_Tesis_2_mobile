package com.example.dermoapp.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class Repository {
    val BASE_URL = "https://1c2d-186-84-88-83.ngrok.io"

    fun retrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(this.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}