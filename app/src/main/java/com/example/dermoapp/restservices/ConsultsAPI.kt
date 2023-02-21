package com.example.dermoapp.restservices

import com.example.dermoapp.daos.ConsultDAO
import retrofit2.Call
import retrofit2.http.*

interface ConsultsAPI {
    @GET("/consults")
    fun index(@Header("Authorization") token: String): Call<List<ConsultDAO>>
}