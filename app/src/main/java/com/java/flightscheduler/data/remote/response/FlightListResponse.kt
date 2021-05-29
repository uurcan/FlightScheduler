package com.java.flightscheduler.data.remote.response

import com.java.flightscheduler.data.model.flight.Dictionaries
import com.java.flightscheduler.data.model.flight.FlightData
import com.java.flightscheduler.data.model.flight.Meta
import com.squareup.moshi.Json

open class FlightListResponse<Item>(
    @Json(name="meta") var meta : Meta? = null,
    @Json(name="data") var data : FlightData? = null,
    @Json(name="dictionaries") var dictionaries: Dictionaries? = null
)