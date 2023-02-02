package com.example.dermoapp.models

import java.util.*

class User(
    var name: String,
    var email: String,
    var date: Date?,
    var city: String,
    var phone: String,
    var password: String?
) {}