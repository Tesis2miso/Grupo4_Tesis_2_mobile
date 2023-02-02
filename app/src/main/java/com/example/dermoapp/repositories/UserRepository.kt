package com.example.dermoapp.repositories

import com.example.dermoapp.daos.UserDAO
import com.example.dermoapp.models.User
import com.example.dermoapp.restservices.UsersAPI
import com.example.dermoapp.utils.ApiError
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


object UserRepository: Repository() {
    private var client: Retrofit = super.retrofitClient()
    private var service: UsersAPI = client.create(UsersAPI::class.java)

    fun createUser(user: User, onSuccess: (user: User) -> Unit, onFailure: (error: ApiError) -> Unit, onNetworkError: () -> Unit, onResponse: () -> Unit) {
        service.create(user).enqueue(object: Callback<UserDAO> {
            override fun onResponse(call: Call<UserDAO>, response: Response<UserDAO>) {
                if(response.isSuccessful) {
                    onSuccess(response.body()!!.toUser())
                } else {
                    val apiError: ApiError = Gson().fromJson(
                        response.errorBody()!!.charStream(),
                        ApiError::class.java
                    )
                    onFailure(apiError)
                }
                onResponse()
            }

            override fun onFailure(call: Call<UserDAO>, t: Throwable) {
                onNetworkError()
                onResponse()
            }
        })
    }
}