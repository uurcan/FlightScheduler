package com.java.flightscheduler.data.remote.repository

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
}