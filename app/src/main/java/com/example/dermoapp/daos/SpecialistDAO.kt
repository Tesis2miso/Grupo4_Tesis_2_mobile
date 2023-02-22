package com.example.dermoapp.daos

import com.example.dermoapp.models.Specialist
import com.fasterxml.jackson.annotation.JsonProperty

data class SpecialistDAO(
    @JsonProperty("id") var id: Int,
    @JsonProperty("email") var email: String,
    @JsonProperty("last_name") var last_name: String,
    @JsonProperty("name") var name: String,
    @JsonProperty("username") var username: String,
) : DAO {
    fun toSpecialist(): Specialist {
        return Specialist(
            email, last_name, name, username, id
        )
    }
}