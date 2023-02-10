package com.example.dermoapp.daos

import com.example.dermoapp.models.User
import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateCityDAO(
    @JsonProperty("city") var city: String,
) {}