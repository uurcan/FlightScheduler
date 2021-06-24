package com.java.flightscheduler.data.repository

import com.java.flightscheduler.data.remote.api.services.MetricsService
import com.java.flightscheduler.data.remote.request.base.BaseApiCall
import com.java.flightscheduler.data.remote.response.FlightInitializer
import com.java.flightscheduler.di.dispatcher.IoDispatcher
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Inject

class MetricsRepository @Inject constructor(
    okHttpClient: OkHttpClient,
    moshi: Moshi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val metricsService : MetricsService
) : BaseApiCall(moshi,dispatcher) {
    override val basePath = "v1/"

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