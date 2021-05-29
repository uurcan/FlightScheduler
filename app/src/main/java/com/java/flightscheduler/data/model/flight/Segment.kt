package com.java.flightscheduler.data.model.flight

data class Segment(
    val aircraft: Aircraft,
    val arrival: Arrival,
    val blacklistedInEU: Boolean,
    val carrierCode: String,
    val departure: Departure,
    val duration: String,
    val id: String,
    val number: String,
    val numberOfStops: Int,
    val operating: Operating
)