package com.java.flightscheduler.data.repository

import com.java.flightscheduler.data.constants.AppConstants.FLIGHT_SEARCH_BASE_URL
import com.java.flightscheduler.data.constants.AppConstants.MIN_ADULT_COUNT
import com.java.flightscheduler.data.constants.AppConstants.MIN_CHILD_COUNT
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.model.seatmap.base.SeatMapSearch
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

    fun getURLFromOffer(seatMapSearch : SeatMapSearch) : String{
        return FLIGHT_SEARCH_BASE_URL +
                "originLocationCode=" + seatMapSearch.originLocationCode + "&" +
                "destinationLocationCode=" + seatMapSearch.destinationLocationCode + "&" +
                "departureDate=" + seatMapSearch.flightDate + "&" +
                "adults=" + "${MIN_ADULT_COUNT}&" +
                "children=" + "${MIN_CHILD_COUNT}&" +
                "max=" + "${1}"
    }

    fun getQueryErrors(errorResults : List<BaseApiResult.Error.Issue>) : String? {
        var errorMessages = ""
        errorResults.forEach {
                error ->
            errorMessages += "${error.code} - ${error.detail} \n"
        }
        return if (errorResults.isEmpty()) "No Seat Map available for this flight."
        else errorMessages
    }
}