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
    const val REQUEST_CODE_CALL_PERMISSION = 101
    const val PAGER_ITEM_SIZE = 4

    const val SEAT_MAP_AVAILABLE = "AVAILABLE"
    const val SEAT_MAP_OCCUPIED = "OCCUPIED"
    const val SEAT_MAP_BLOCKED = "BLOCKED"

    const val DECK_LARGE = 11
    const val DECK_MEDIUM = 7
    const val DECK_SMALL = 5

    const val FLIGHT_SEARCH_BASE_URL = "https://test.api.amadeus.com/v2/shopping/flight-offers?"
    enum class FilterOptions(val param : String) {
        Default("none"),
        Name("name"),
        Price("price"),
        Distance("distance"),
        Rating("rating")
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