package com.example.dermoapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.dermoapp.models.Model

class SharedPreferencesManager(val context: Context) {
    companion object {
        val USER_TOKEN: String = "USER_TOKEN"
        val USER: String = "USER"
        val PREFERENCES: String = "PREFERENCES"
    }

    fun getStringPreference(key: String): String? {
        val sharedPreference =  context.getSharedPreferences(
            SharedPreferencesManager.PREFERENCES, Context.MODE_PRIVATE
        )
        return sharedPreference.getString(key, null)
    }

    fun saveStringPreference(key: String, value: String) {
        val preferences: SharedPreferences = context.getSharedPreferences(
            SharedPreferencesManager.PREFERENCES, Context.MODE_PRIVATE
        )
        val editor = preferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun saveModelPreference(key: String, value: Model) {
        val preferences: SharedPreferences = context.getSharedPreferences(
            SharedPreferencesManager.PREFERENCES, Context.MODE_PRIVATE
        )
        val editor = preferences.edit()
        val json = value.asGsonString()
        editor.putString(key, json)
        editor.apply()
    }

    fun getModelPreference(key: String, className: Class<*>): Model {
        val sharedPreference =  context.getSharedPreferences(
            SharedPreferencesManager.PREFERENCES, Context.MODE_PRIVATE
        )
        val json: String = sharedPreference.getString(key, "")!!
        return Model.fromGsonString(json, className)
    }

    fun deleteAllPreferences() {
        val preferences: SharedPreferences = context.getSharedPreferences(
            SharedPreferencesManager.PREFERENCES, Context.MODE_PRIVATE
        )
        val editor = preferences.edit()
        editor.clear();
        editor.apply();
    }
}