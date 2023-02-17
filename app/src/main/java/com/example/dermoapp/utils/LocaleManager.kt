package com.example.dermoapp.utils

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import com.example.dermoapp.R
import java.util.*


class LocaleManager(private val activity: Activity) {
    companion object {
        val SPANISH: String = "es"
        val ENGLISH: String = "en"
    }

    @SuppressWarnings("deprecation")
    fun setLocale(languageCode: String) {
        val config: Configuration = activity.baseContext.resources.configuration

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        config.locale = locale;
        activity.baseContext.resources.updateConfiguration(
            config, activity.baseContext.resources.displayMetrics
        );
    }

    @SuppressWarnings("deprecation")
    fun getCurrentLocale(): String {
        var locale: Locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = activity.resources.configuration.locales[0]
        } else {
            locale = activity.resources.configuration.locale
        }

        return if(locale.toString().contains("es")) {
            activity.resources?.getString(R.string.spanish)!!
        } else {
            activity.resources?.getString(R.string.english)!!
        }
    }
}