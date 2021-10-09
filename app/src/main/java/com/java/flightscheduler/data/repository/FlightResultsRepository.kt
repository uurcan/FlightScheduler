package com.java.flightscheduler.data.repository


import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.flight.FlightOffer

class FlightResultsRepository constructor(private val flightOffer: FlightOffer)
{
    fun getResults() = flightOffer
    fun getPriceResults() = flightOffer.price?.grandTotal.toString() + "€"
    fun getItineraries() = flightOffer.itineraries?.get(0)
    fun getSegments() = flightOffer.itineraries?.get(0)?.segments?.get(0)
    fun getCarrierCode() = getSegments()?.carrierCode
    fun getFlightNumber() = getSegments()?.carrierCode + "-" + getSegments()?.number
    fun getDuration() = flightOffer.itineraries?.get(0)?.duration

    fun getQueryErrors(errorResults : List<BaseApiResult.Error.Issue>) : String? {
        var errorMessages : String? = "Following errors found : \n \n"
        errorResults.forEach {
            error ->
                errorMessages += "${error.code} - ${error.detail} \n"
        }
        return errorMessages
    }
}