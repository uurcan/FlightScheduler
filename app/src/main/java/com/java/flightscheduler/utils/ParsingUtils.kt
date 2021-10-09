package com.java.flightscheduler.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.Instant

open class ParsingUtils {

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
    fun dateParser(parser : SimpleDateFormat,formatter : SimpleDateFormat ,date : String?) : String {
        return formatter.format(
            parser.parse(
                date
            )
        )
    }

    @SuppressLint("NewApi")
    fun getCurrentDate(): String {
        return Instant.now().toString()
    }
}