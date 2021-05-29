package com.java.flightscheduler.data.model.flight

data class TravelerPricing(
    val fareDetailsBySegment: List<FareDetailsBySegment>,
    val fareOption: String,
    val price: PriceX,
    val travelerId: String,
    val travelerType: String
)