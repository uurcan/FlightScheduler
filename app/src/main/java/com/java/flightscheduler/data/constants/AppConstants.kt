package com.java.flightscheduler.data.constants

import com.java.flightscheduler.data.model.base.Language

object AppConstants {
    const val MIN_ADULT_COUNT = 1
    const val MAX_ADULT_COUNT = 3
    const val MIN_CHILD_COUNT = 0
    const val MAX_CHILD_COUNT = 6
    const val MIN_ROOM_COUNT = 1
    const val MAX_ROOM_COUNT = 9
    const val MIN_AUDIT_COUNT = 1
    const val MAX_AUDIT_COUNT = 9

    enum class FilterOptions(val param : String) {
        Default("DEFAULT"),
        Name("NAME"),
        Price("PRICE"),
        Distance("DISTANCE"),
        Rating("RATING")
    }
    enum class LanguageOptions(val code : String) {
        Chinese("ZH"),
        Spanish("ES"),
        English("FR"),
        Arabian("AR"),
        German("DE"),
        Hindi("HI"),
        Russian("RU"),
        Japanese("JA"),
        Turkish("TR")
    }
}