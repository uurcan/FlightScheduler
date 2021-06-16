package com.java.flightscheduler.data.remote.api

import com.java.flightscheduler.data.remote.api.services.MetricsService
import com.java.flightscheduler.data.remote.request.base.BaseApiCall
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class MetricsSearch internal constructor(
    baseUrl: String,
    httpClient: OkHttpClient,
    moshi : Moshi,
    dispatcher: CoroutineDispatcher
) : BaseApiCall(moshi,dispatcher) {
    override val basePath = "v1/"

    private val metricsService : MetricsService = Retrofit.Builder()
        .baseUrl(baseUrl + basePath)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(httpClient)
        .build()
        .create()

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