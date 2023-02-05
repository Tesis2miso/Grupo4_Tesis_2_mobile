package com.example.dermoapp.restservices

import com.example.dermoapp.daos.UserDAO
import com.example.dermoapp.daos.UserLoginDAO
import com.example.dermoapp.models.User
import com.example.dermoapp.models.UserLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UsersAPI {
    @POST("/users")
    fun create(@Body user: User): Call<UserDAO>

    @POST("/users/login")
    fun login(@Body userLogin: UserLogin): Call<UserLoginDAO>
}