package com.example.dermoapp.models

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.util.*

class User(
    var name: String,
    var email: String,
    @SerializedName("birth_day")
    var birthDay: String?,
    var city: String,
    var phone: String,
    var password: String?,
    var token: String? = null
): Model {
}