package com.java.flightscheduler.data.repository


import com.java.flightscheduler.data.model.flight.FlightOffer
import javax.inject.Inject

class FlightResultsRepository @Inject constructor() {
    fun getPriceResults(flightOffer: FlightOffer) = flightOffer.price?.grandTotal.toString() + "€"
    fun getSegments(flightOffer: FlightOffer) = flightOffer.itineraries?.get(0)?.segments?.get(0)
    fun getCarrierCode(flightOffer: FlightOffer) = getSegments(flightOffer)?.carrierCode
    fun getFlightNumber(flightOffer: FlightOffer) = getSegments(flightOffer)?.carrierCode + "-" + getSegments(flightOffer)?.number
    fun getDuration(flightOffer: FlightOffer) = flightOffer.itineraries?.get(0)?.duration
}