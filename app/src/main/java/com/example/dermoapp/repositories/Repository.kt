package com.example.dermoapp.repositories

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class Repository {
    val BASE_URL = "http://dermoapp-server.eba-u5i6h72y.us-east-1.elasticbeanstalk.com"

    fun retrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(this.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}