package com.example.dermoapp.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.gson.annotations.SerializedName
import java.util.*

class Specialist(
    var email: String,
    var lastName: String,
    var name: String,
    var username: String,
    var id: Int? = null
): Model {
}