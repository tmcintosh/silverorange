package com.silverorange.videoplayer.util

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

object MockUtils {
    @SuppressLint("SimpleDateFormat")
    fun stringToDate(stringDate: String): Date? {
        var date: Date? = null
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        try {
            date = format.parse(stringDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }
}

