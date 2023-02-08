package com.example.dermoapp.utils

import android.text.Editable

class CapsUtil {

     fun hasCapsOn(text: Editable?): Boolean{
        if (text == null){
            return false
        }
        if(text.isNotEmpty()){
            return text.last().isUpperCase()
        }
        return false
    }
}