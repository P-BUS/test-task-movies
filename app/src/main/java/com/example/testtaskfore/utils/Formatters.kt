package com.example.testtaskfore.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

const val BASIC_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val OUTPUT_FORMAT = "dd.MM.yyyy"

object Formatters {

    private const val TAG = "Formatters"

    fun formatDateFromString(inputFormat: String?, outputFormat: String?, inputDate: String?): String {
        val input = SimpleDateFormat(inputFormat, Locale.getDefault())
        val output = SimpleDateFormat(outputFormat, Locale.getDefault())
        if (inputDate == null) {
            return ""
        }
        return try {
            val parsed = input.parse(inputDate)
            output.format(parsed)
        } catch (e: ParseException) {
            Log.e(TAG, "$TAG::formatDateFromString - ParseException - dateFormat")
            ""
        }
    }
}