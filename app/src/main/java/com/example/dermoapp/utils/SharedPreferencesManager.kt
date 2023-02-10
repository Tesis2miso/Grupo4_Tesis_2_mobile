package com.example.dermoapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(val context: Context) {
    companion object {
        val USER_TOKEN: String = "USER_TOKEN"
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

    fun deleteAllPreferences() {
        val preferences: SharedPreferences = context.getSharedPreferences(
            SharedPreferencesManager.PREFERENCES, Context.MODE_PRIVATE
        )
        val editor = preferences.edit()
        editor.clear();
        editor.apply();
    }
}