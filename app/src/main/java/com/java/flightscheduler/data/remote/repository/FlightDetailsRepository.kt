package com.java.flightscheduler.data.remote.repository

import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import javax.inject.Inject

class FlightDetailsRepository {

    fun getAircraft(segment: SearchSegment) = segment.aircraft?.code
    fun getOrigin(segment: SearchSegment) = segment.departure?.iataCode
    fun getDestination(segment: SearchSegment) = segment.arrival?.iataCode
    fun getDepartureDate(segment: SearchSegment) = segment.departure?.at
    fun getArrivalDate(segment: SearchSegment) = segment.arrival?.at
    fun getFlightCode(segment: SearchSegment) = segment.carrierCode + " - " + segment.number
    fun getDuration(segment: SearchSegment) = segment.duration


    fun getLegCount(flightOffer: FlightOffer) : Int? {
        val itinerary = flightOffer.itineraries
        return if (itinerary?.size == 1){
            itinerary[0].segments?.size
        } else {
            itinerary?.get(0)?.segments?.size?.plus(itinerary[1].segments!!.size)
        }
    }

    fun getFlightDetails(flightOffer: FlightOffer) : List<SearchSegment>? {
        val itinerary = flightOffer.itineraries
        return if (itinerary?.size == 1){
            itinerary[0].segments
        } else {
            val firstSegment = itinerary?.get(0)?.segments
            val secondSegment = itinerary?.get(1)?.segments

            merge(firstSegment,secondSegment)
        }
    }

    private fun <T> merge(first: List<T>?, second: List<T>?): List<T> {
        return first!!.plus(second!!)
    }

}