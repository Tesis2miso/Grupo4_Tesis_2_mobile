package com.example.dermoapp.daos

import com.example.dermoapp.models.User
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class UserDAO(
    @JsonProperty("id") var id: Int,
    @JsonProperty("name") var name: String,
    @JsonProperty("email") var email: String,
    @JsonProperty("phone") var phone: String,
    @JsonProperty("city") var city: String,
    @JsonProperty("birth_day") var birth_day: String,
    @JsonProperty("token") var token: String,
) {
    fun toUser(): User {
        return User(
            name, email, birth_day,
            city, phone, null, token
        )
    }
}