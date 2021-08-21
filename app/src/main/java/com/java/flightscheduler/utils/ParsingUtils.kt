package com.java.flightscheduler.utils

class ParsingUtils {
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
}