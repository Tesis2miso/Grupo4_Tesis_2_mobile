package com.example.dermoapp.models

import java.util.*

class User(
    var name: String,
    var email: String,
    var birth_day: String?,
    var city: String,
    var phone: String,
    var password: String?,
    var token: String? = null
) {}