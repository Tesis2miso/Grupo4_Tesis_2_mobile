package com.example.dermoapp.repositories

import android.content.Context
import com.example.dermoapp.daos.UpdateCityDAO
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
        service.create(user).enqueue(object: Callback<UserDAO> {
            override fun onResponse(call: Call<UserDAO>, response: Response<UserDAO>) {
                if(response.isSuccessful) {
                    onSuccess(response.body()!!.toUser())
                } else {
                    onFailure(userRetrofitErrorToApiError(response))
                }
                onResponse()
            }

            override fun onFailure(call: Call<UserDAO>, t: Throwable) {
                onNetworkError()
                onResponse()
            }
        })
    }

    fun loginUser(userLogin: User, onSuccess: (userLogin: User) -> Unit, onFailure: (error: ApiError) -> Unit, onNetworkError: () -> Unit, onResponse: () -> Unit) {
        service.login(userLogin).enqueue(object: Callback<UserDAO> {
            override fun onResponse(call: Call<UserDAO>, response: Response<UserDAO>) {
                if(response.isSuccessful) {
                    onSuccess(response.body()!!.toUser())
                } else {
                    onFailure(userRetrofitErrorToApiError(response))
                }
                onResponse()
            }

            override fun onFailure(call: Call<UserDAO>, t: Throwable) {
                onNetworkError()
                onResponse()
            }
        })
    }

    fun updateCity(context: Context, city: String, onSuccess: (user: User) -> Unit, onFailure: (error: ApiError) -> Unit, onNetworkError: () -> Unit, onResponse: () -> Unit) {
        val token = authHeader(context)
        val dao = UpdateCityDAO(city)
        service.updateCity(token, dao).enqueue(object: Callback<UserDAO> {
            override fun onResponse(call: Call<UserDAO>, response: Response<UserDAO>) {
                if(response.isSuccessful) {
                    onSuccess(response.body()!!.toUser())
                } else {
                    onFailure(userRetrofitErrorToApiError(response))
                }
                onResponse()
            }

            override fun onFailure(call: Call<UserDAO>, t: Throwable) {
                onNetworkError()
                onResponse()
            }
        })
    }

    private fun userRetrofitErrorToApiError(response: Response<UserDAO>): ApiError {
        return Gson().fromJson(
            response.errorBody()!!.charStream(),
            ApiError::class.java
        )
    }

    private fun authHeader(context: Context): String {
        val token = SharedPreferencesManager(context).getStringPreference(
            SharedPreferencesManager.USER_TOKEN
        )
        return "Bearer $token"
    }
}