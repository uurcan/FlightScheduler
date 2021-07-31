package com.java.flightscheduler.data.model.flight
class FlightSearch {
    val originLocationCode: String
    val destinationLocationCode: String
    val originLocationCity: String
    val destinationLocationCity: String
    val departureDate: String
    val adults: Int
    val children: Int?
    val formattedDepartureDate: String
    val infants : Int? = 0
    var returnDate : String? = null
    val includedAirlineCodes : String? = null
    val excludedAirlineCodes : String? = null
    val nonStop : Boolean? = false
    val currencyCode: String? = null
    val maxPrice: Int? = 0
    val max : Int? = 10

    constructor (
        originLocationCode: String,
        destinationLocationCode: String,
        originLocationCity: String,
        destinationLocationCity: String,
        departureDate: String,
        returnDate : String,
        adults: Int,
        children: Int?,
        formattedDepartureDate: String,
    ) {
        this.originLocationCode = originLocationCode
        this.destinationLocationCode = destinationLocationCode
        this.originLocationCity = originLocationCity
        this.destinationLocationCity = destinationLocationCity
        this.departureDate = departureDate
        this.returnDate = returnDate
        this.adults = adults
        this.children = children
        this.formattedDepartureDate = formattedDepartureDate
    }

    constructor (
        originLocationCode: String,
        destinationLocationCode: String,
        originLocationCity: String,
        destinationLocationCity: String,
        departureDate: String,
        adults: Int,
        children: Int?,
        formattedDepartureDate: String
    ) {
        this.originLocationCode = originLocationCode
        this.destinationLocationCode = destinationLocationCode
        this.originLocationCity = originLocationCity
        this.destinationLocationCity = destinationLocationCity
        this.departureDate = departureDate
        this.adults = adults
        this.children = children
        this.formattedDepartureDate = formattedDepartureDate
    }
}