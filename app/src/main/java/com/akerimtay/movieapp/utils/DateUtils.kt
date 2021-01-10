package com.akerimtay.movieapp.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @JvmStatic
    fun getYear(date: Date?): String? {
        return date?.let {
            val calendar = Calendar.getInstance()
            calendar.time = it
            calendar.get(Calendar.YEAR).toString()
        }
    }

    @JvmStatic
    fun getDate(date: Date?): String? {
        return date?.let {
            SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(date)
        }
    }
}