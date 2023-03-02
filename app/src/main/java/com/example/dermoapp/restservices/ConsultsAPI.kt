package com.example.dermoapp.restservices

import com.example.dermoapp.daos.ConsultDAO
import com.example.dermoapp.daos.CreateConsultDAO
import com.example.dermoapp.daos.UpdateStatusDAO
import retrofit2.Call
import retrofit2.http.*

interface ConsultsAPI {
    @GET("/consults")
    fun index(@Header("Authorization") token: String): Call<List<ConsultDAO>>

    @POST("/consults")
    fun create(@Header("Authorization") token: String, @Body consult: CreateConsultDAO): Call<ConsultDAO>

    @PUT("/consults/{consult_id}")
    fun update(@Path("consult_id") userId: Int, @Header("Authorization") token: String, @Body status: UpdateStatusDAO): Call<ConsultDAO>
}