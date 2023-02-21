package com.example.dermoapp.repositories

import android.content.Context
import com.example.dermoapp.daos.ConsultDAO
import com.example.dermoapp.daos.UpdateCityDAO
import com.example.dermoapp.daos.UpdateUserDAO
import com.example.dermoapp.daos.UserDAO
import com.example.dermoapp.models.Consult
import com.example.dermoapp.models.User
import com.example.dermoapp.restservices.ConsultsAPI
import com.example.dermoapp.restservices.UsersAPI
import com.example.dermoapp.utils.ApiError
import com.example.dermoapp.utils.SharedPreferencesManager
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


object ConsultRepository : Repository() {
    private var client: Retrofit = super.retrofitClient()
    private var service: ConsultsAPI = client.create(ConsultsAPI::class.java)

    fun loadConsults(
        context: Context,
        onSuccess: (consults: List<Consult>) -> Unit,
        onFailure: (error: ApiError) -> Unit,
        onNetworkError: () -> Unit,
        onResponse: () -> Unit
    ) {
        val token = authHeader(context)
        service.index(token).enqueue(object : Callback<List<ConsultDAO>> {
            override fun onResponse(
                call: Call<List<ConsultDAO>>,
                response: Response<List<ConsultDAO>>
            ) {
                if (response.isSuccessful) {
                    val consults: List<Consult> = response.body()!!.map { dao ->
                        dao.toConsult()
                    }
                    onSuccess(consults)
                } else {
                    val apiError: ApiError = Gson().fromJson(
                        response.errorBody()!!.charStream(),
                        ApiError::class.java
                    )
                    onFailure(apiError)
                }
                onResponse()
            }

            override fun onFailure(call: Call<List<ConsultDAO>>, t: Throwable) {
                onNetworkError()
                onResponse()
            }
        })
    }

    private fun authHeader(context: Context): String {
        val token = SharedPreferencesManager(context).getStringPreference(
            SharedPreferencesManager.USER_TOKEN
        )
        return "Bearer $token"
    }
}