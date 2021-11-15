package com.java.flightscheduler.utils

import android.annotation.SuppressLint
import android.content.Context
import com.java.flightscheduler.R
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

object ParsingUtils {

    fun crop (word : String ) : String{
        val wordArray = word.split(" ")
        return if (word.length >= 8) {
            when (wordArray.size) {
                1 -> word
                2 -> wordArray[0][0] + ". " + wordArray[1]
                else -> {
                    wordArray[0][0] + ". " + wordArray[1][0] + ". " + wordArray[2]
                }
            }
        } else {
            word
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun dateParser(parser : SimpleDateFormat,formatter : SimpleDateFormat ,date : String?) : String? {
        return if (date != null) {
            formatter.format(
                parser.parse(
                    date
                )
            )
        } else {
            "No date found."
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun datePickerParser(context : Context?, date: String?) : String? {
        val parser = SimpleDateFormat(context?.getString(R.string.text_date_parser_format), Locale.ENGLISH)
        val formatter = SimpleDateFormat(context?.getString(R.string.text_date_formatter), Locale.ENGLISH)
        return if (date != null) {
            formatter.format(
                parser.parse(
                    date
                )
            )
        } else {
            "No date found."
        }
    }

    @JvmStatic
    @SuppressLint("NewApi")
    fun getCurrentDate(defaultDate : String?): String {
        return defaultDate ?: Instant.now().toString()
    }

    @JvmStatic
    @SuppressLint("NewApi")
    fun getNextDate(defaultDate : String?): String? {
        return defaultDate ?: Instant.now().plus(1,ChronoUnit.DAYS).toString()
    }
}