package com.java.flightscheduler.data.constants

import com.java.flightscheduler.BuildConfig

object HttpConstants {
    const val BASE_PATH = "v1/"
    const val GET = "GET"
    const val POST = "POST"
    const val DELETE = "DELETE"
    const val AUTH = "Authorization"
    const val clientId = BuildConfig.API_KEY
    const val clientSecret = BuildConfig.API_SECRET

    const val SEAT_MAP_REQUEST_HEADER = "{\"meta\":{},\"data\":["
    const val SEAT_MAP_REQUEST_FOOTER = "],\"dictionaries\":{\"locations\":{}}}"
}