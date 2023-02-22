package com.example.dermoapp.utils

import android.annotation.SuppressLint
import android.content.Context
import com.example.dermoapp.R

class StatusUtil {
    companion object {
        val PENDING = 0
        val CONFIRMED = 1
        val REJECTED = 2

        fun statusToString(context: Context, status: Int): String {
            var string = ""
            if(status == PENDING) {
                string = context.getString(R.string.pending_status)
            } else if(status == CONFIRMED) {
                string = context.getString(R.string.confirmed_status)
            } else if(status == REJECTED) {
                string = context.getString(R.string.rejected_status)
            }
            return string
        }

        @SuppressLint("NewApi")
        fun statusToColor(context: Context, status: Int): Int {
            var color = context.getColor(R.color.black)
            if(status == PENDING) {
                color = context.getColor(R.color.pending_status)
            } else if(status == CONFIRMED) {
                color = context.getColor(R.color.confirmed_status)
            } else if(status == REJECTED) {
                color = context.getColor(R.color.rejected_status)
            }
            return color
        }
    }
}