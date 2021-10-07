package com.java.flightscheduler.utils

import android.content.Context
import com.java.flightscheduler.R
import java.text.SimpleDateFormat
import java.util.*

open class ParsingUtils (context: Context?){

    val parser = SimpleDateFormat(context?.getString(R.string.text_date_parser_format), Locale.ENGLISH)
    val formatter = SimpleDateFormat(context?.getString(R.string.text_date_formatter), Locale.ENGLISH)

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
    fun getCurrentDate(): String {
        return Calendar.getInstance().time.toString()
    }
}