package com.java.flightscheduler.data.model.flight

data class Itinerary(
    val duration: String,
    val segments: List<Segment>
)