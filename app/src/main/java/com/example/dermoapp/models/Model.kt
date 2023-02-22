package com.example.dermoapp.models

import com.google.gson.Gson

interface Model {
    fun asGsonString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }

    companion object {
        fun fromGsonString(stringGson: String, className: Class<*>): Model {
            val gson = Gson()
            return gson.fromJson(stringGson, className) as Model
        }
    }
}