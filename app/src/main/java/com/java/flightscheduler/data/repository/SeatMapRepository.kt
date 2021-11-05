package com.java.flightscheduler.data.repository

import com.java.flightscheduler.data.constants.AppConstants.FLIGHT_SEARCH_BASE_URL
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.remote.services.SeatMapService
import com.java.flightscheduler.data.remote.request.base.BaseApiCall
import com.java.flightscheduler.di.dispatcher.IoDispatcher
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeatMapRepository @Inject constructor(
    moshi: Moshi,
    @IoDispatcher val dispatcher: CoroutineDispatcher,
    private val seatMapService: SeatMapService
) : BaseApiCall(moshi,dispatcher) {

    @Throws(Exception::class)
    suspend fun get(url: String): String {
        return withContext(dispatcher) {
            seatMapService.getSeatMapFromFlightURL(url).string()
        }
    }

    @Throws(Exception::class)
    suspend fun post(body: String) = baseApiCall {
        seatMapService.getSeatMapFromFlightOffer(bodyAsMap(body))
    }

    fun getURLFromOffer(flightSearch : FlightSearch) : String{
        return FLIGHT_SEARCH_BASE_URL +
                "originLocationCode=" + flightSearch.originLocationCode + "&" +
                "destinationLocationCode=" + flightSearch.destinationLocationCode + "&" +
                "departureDate=" + flightSearch.departureDate + "&" +
                "returnDate=" + "${flightSearch.returnDate?:""}&" +
                "adults=" + "${flightSearch.adults}&" +
                "children=" + "${flightSearch.children}&" +
                "max=" + "${1}"
    }
}