package com.example.dermoapp.daos

import com.example.dermoapp.models.UserLogin
import com.fasterxml.jackson.annotation.JsonProperty

data class UserLoginDAO(@JsonProperty("token") var token: String){

    fun toUserLogin(): UserLogin {
        return UserLogin(null, null, token)
    }
}
