package com.example.dermoapp.restservices

import com.example.dermoapp.daos.UpdateCityDAO
import com.example.dermoapp.daos.UpdateUserDAO
import com.example.dermoapp.daos.UserDAO
import com.example.dermoapp.models.User
import retrofit2.Call
import retrofit2.http.*

interface UsersAPI {
    @POST("/users")
    fun create(@Body user: User): Call<UserDAO>

    @POST("/users/login")
    fun login(@Body userLogin: User): Call<UserDAO>

    @PUT("/users/update_city")
    fun updateCity(@Header("Authorization") token: String, @Body city: UpdateCityDAO): Call<UserDAO>

    @PUT("/users/{user_id}")
    fun updateUserDetail(@Path("user_id") userId: Int, @Header("Authorization") token: String, @Body user: UpdateUserDAO): Call<UserDAO>
}