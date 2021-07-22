package com.java.flightscheduler.data.model.flight

class FlightSearch(
    val originLocationCode: String,
    val destinationLocationCode: String,
    val departureDate: String,
    val returnDate: String,
    val adults: Int,
    val children: Int?,
    var formattedDepartureDate : String? = null,
    var originLocationCity : String? = null,
    var destinationLocationCity : String? = null
) {
    val infants : Int? = 0
    val includedAirlineCodes : String? = null
    val excludedAirlineCodes : String? = null
    val nonStop : Boolean? = false
    val currencyCode: String? = null
    val maxPrice: Int? = 0
    val max : Int? = 10
}
