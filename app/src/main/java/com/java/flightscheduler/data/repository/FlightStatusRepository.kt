package com.java.flightscheduler.data.repository

import com.java.flightscheduler.data.remote.services.FlightStatusService
import com.java.flightscheduler.data.remote.request.base.BaseApiCall
import com.java.flightscheduler.di.dispatcher.IoDispatcher
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FlightStatusRepository @Inject constructor(
    moshi: Moshi,
    private val flightStatusService: FlightStatusService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseApiCall(moshi, dispatcher) {

    suspend fun get(
        carrierCode: String,
        flightNumber: Int,
        scheduledDepartureDate: String,
        operationalSuffix: String? = null
    ) = baseApiCall {
        flightStatusService.getFlightStatus(
            carrierCode = carrierCode,
            flightNumber = flightNumber,
            scheduledDepartureDate = scheduledDepartureDate,
            operationalSuffix = operationalSuffix
        )
    }
}