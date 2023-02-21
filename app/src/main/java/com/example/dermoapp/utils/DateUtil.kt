package com.example.dermoapp.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    companion object {
        fun dateToString(dateToFormat: Date): String {
            val simpleFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            return simpleFormat.format(dateToFormat)
        }

        fun stringToDate(dateString: String?): Date? {
            val simpleFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            return dateString?.let { simpleFormat.parse(it) }
        }
    }
}