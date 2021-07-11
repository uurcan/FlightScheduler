package com.java.flightscheduler.data.remote.repository

import com.java.flightscheduler.data.remote.services.MetricsService
import com.java.flightscheduler.data.remote.request.base.BaseApiCall
import com.java.flightscheduler.di.dispatcher.IoDispatcher
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MetricsRepository @Inject constructor(
    moshi: Moshi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val metricsService : MetricsService
) : BaseApiCall(moshi,dispatcher) {

    suspend fun get(
        originIataCode : String? = null,
        destinationIataCode : String? = null,
        departureDate : String? = null,
        currencyCode : String? = null,
        oneWay : Boolean? = false
    ) = baseApiCall {
        metricsService.getItineraryPriceMetrics(
            originIataCode = originIataCode,
            destinationIataCode =  destinationIataCode,
            departureDate = departureDate,
            currencyCode = currencyCode,
            oneWay = oneWay
        )
    }
}