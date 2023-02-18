package com.example.dermoapp.daos

import com.example.dermoapp.models.User
import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class UserDAO(
    @JsonProperty("id") var id: Int,
    @JsonProperty("name") var name: String,
    @JsonProperty("email") var email: String,
    @JsonProperty("phone") var phone: String,
    @JsonProperty("city") var city: String,
    @SerializedName("birth_day") var birthDay: String,
    @JsonProperty("token") var token: String,
): DAO {
    fun toUser(): User {
        return User(
            name, email, birthDay,
            city, phone, null, token, id
        )
    }
}