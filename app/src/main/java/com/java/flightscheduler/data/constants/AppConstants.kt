package com.java.flightscheduler.data.constants

import com.java.flightscheduler.data.model.hotel.base.Language
import com.java.flightscheduler.data.model.hotel.base.SortOption
import com.java.flightscheduler.data.model.metrics.Currency

object AppConstants {
    const val MIN_ADULT_COUNT = 1
    const val MAX_ADULT_COUNT = 3
    const val MIN_CHILD_COUNT = 0
    const val MAX_CHILD_COUNT = 6
    const val MIN_ROOM_COUNT = 1
    const val MAX_ROOM_COUNT = 9
    const val MIN_PASSENGER_COUNT = 1
    const val MAX_PASSENGER_COUNT = 9
    const val MIN_LEG_COUNT = 1
    const val MAX_LEG_COUNT = 3
    const val REQUEST_CODE_CALL_PERMISSION = 101
    const val PAGER_ITEM_SIZE = 4

    const val SEAT_MAP_AVAILABLE = "AVAILABLE"
    const val SEAT_MAP_OCCUPIED = "OCCUPIED"
    const val SEAT_MAP_BLOCKED = "BLOCKED"
    const val SEAT_MAP_AISLE = "AISLE"

    const val TWELVE_SEAT_DECK = 12
    const val ELEVEN_SEAT_DECK = 11
    const val TEN_SEAT_DECK = 10
    const val NINE_SEAT_DECK = 9
    const val SIX_SEAT_DECK = 7
    const val FOUR_SEAT_DECK = 5

    const val FLIGHT_SEARCH_BASE_URL = "https://test.api.amadeus.com/v2/shopping/flight-offers?"

    val FilterOptions = listOf(
        SortOption("Default", "none"),
        SortOption("Name", "name"),
        SortOption("Price", "price"),
        SortOption("Distance", "distance"),
        SortOption("Rating", "rating")
    )

    val LanguageOptions = listOf(
        Language("Chinese", "ZH"),
        Language("Spanish", "ES"),
        Language("English", "EN"),
        Language("French", "FR"),
        Language("Arabian", "AR"),
        Language("German", "DE"),
        Language("Hindi", "HI"),
        Language("Russian", "RU"),
        Language("Japanese", "JA"),
        Language("Turkish", "TR"),
    )

    val CurrencyOptions = listOf(
        Currency("USD","US Dollar", "$"),
        Currency("EUR","EURO", "?"),
        Currency("JPY","Japanese YEN", "¥"),
        Currency("GBP","Pound Sterling", "£"),
        Currency("TRY","Turkish Lira", "?")
    )
}