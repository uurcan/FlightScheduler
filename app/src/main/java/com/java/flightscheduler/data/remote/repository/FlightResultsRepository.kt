package com.java.flightscheduler.data.remote.repository


import com.java.flightscheduler.data.model.flight.FlightOffer

class FlightResultsRepository constructor(private val flightOffer: FlightOffer
) {
    fun getResults() = flightOffer
    fun getPriceResults() = flightOffer.price?.grandTotal.toString() + "€"
    fun getItineraries() = flightOffer.itineraries?.get(0)
    fun getSegments() = flightOffer.itineraries?.get(0)?.segments?.get(0)
    fun getCarrierCode() = getSegments()?.carrierCode
    fun getFlightNumber() = getSegments()?.carrierCode + "-" + getSegments()?.number
    fun getDuration() = getSegments()?.duration
    fun getOrigin() = getSegments()?.departure?.iataCode
    fun getDestination() = getSegments()?.arrival?.iataCode
}