package com.example.dermoapp.daos

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName

data class UpdateUserDAO(
    @JsonProperty("name") var name: String,
    @JsonProperty("email") var email: String,
    @JsonProperty("phone") var phone: String,
    @JsonProperty("city") var city: String,
    @SerializedName("birth_day") var birthDay: String?,
): DAO