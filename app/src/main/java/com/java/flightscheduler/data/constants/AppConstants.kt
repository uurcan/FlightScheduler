package com.java.flightscheduler.data.constants

object AppConstants {
    const val MIN_ADULT_COUNT = 1
    const val MAX_ADULT_COUNT = 3
    const val MIN_CHILD_COUNT = 0
    const val MAX_CHILD_COUNT = 6
    const val MIN_ROOM_COUNT = 1
    const val MAX_ROOM_COUNT = 9
    const val MIN_AUDIT_COUNT = 1
    const val MAX_AUDIT_COUNT = 9

    enum class FilterOptions {
        NAME,PRICE,DISTANCE,RATING
    }
}