package com.java.flightscheduler.data.model.flight

data class FlightInfo constructor(
    val carrier: Airlines,
    val origin: String,
    val destination: String?
)
