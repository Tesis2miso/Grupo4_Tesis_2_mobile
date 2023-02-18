package com.example.dermoapp.repositories

import android.content.Context
import com.example.dermoapp.daos.UpdateCityDAO
import com.example.dermoapp.daos.UpdateUserDAO
import com.example.dermoapp.daos.UserDAO
import com.example.dermoapp.models.User
import com.example.dermoapp.restservices.UsersAPI
import com.example.dermoapp.utils.ApiError
import com.example.dermoapp.utils.SharedPreferencesManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


object UserRepository: Repository() {
    private var client: Retrofit = super.retrofitClient()
    private var service: UsersAPI = client.create(UsersAPI::class.java)

    fun createUser(user: User, onSuccess: (user: User) -> Unit, onFailure: (error: ApiError) -> Unit, onNetworkError: () -> Unit, onResponse: () -> Unit) {
        service.create(user).enqueue(
            generateUserCallback(onSuccess, onFailure, onNetworkError, onResponse)
        )
    }

    fun loginUser(userLogin: User, onSuccess: (userLogin: User) -> Unit, onFailure: (error: ApiError) -> Unit, onNetworkError: () -> Unit, onResponse: () -> Unit) {
        service.login(userLogin).enqueue(
            generateUserCallback(onSuccess, onFailure, onNetworkError, onResponse)
        )
    }

    fun updateCity(context: Context, city: String, onSuccess: (user: User) -> Unit, onFailure: (error: ApiError) -> Unit, onNetworkError: () -> Unit, onResponse: () -> Unit) {
        val token = authHeader(context)
        val dao = UpdateCityDAO(city)
        service.updateCity(token, dao).enqueue(
            generateUserCallback(onSuccess, onFailure, onNetworkError, onResponse)
        )
    }

    fun updateUserDetail(context: Context, user: User, onSuccess: (user: User) -> Unit, onFailure: (error: ApiError) -> Unit, onNetworkError: () -> Unit, onResponse: () -> Unit) {
        val token = authHeader(context)
        val dao = UpdateUserDAO(user.name, user.email, user.phone, user.city, user.birthDay)
        service.updateUserDetail(user.id!!, token, dao).enqueue(
            generateUserCallback(onSuccess, onFailure, onNetworkError, onResponse)
        )

    }

    private fun generateUserCallback(onSuccess: (user: User) -> Unit, onFailure: (error: ApiError) -> Unit, onNetworkError: () -> Unit, onResponse: () -> Unit): Callback<UserDAO> {
        return object : Callback<UserDAO> {
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
        }
    }

    private fun authHeader(context: Context): String {
        val token = SharedPreferencesManager(context).getStringPreference(
            SharedPreferencesManager.USER_TOKEN
        )
        return "Bearer $token"
    }
}