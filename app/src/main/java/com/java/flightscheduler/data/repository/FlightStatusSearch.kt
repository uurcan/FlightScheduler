package com.java.flightscheduler.data.repository

import com.java.flightscheduler.data.remote.api.services.FlightStatusService
import com.java.flightscheduler.data.remote.request.base.BaseApiCall
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class FlightStatusSearch internal constructor(
    baseUrl: String,
    httpClient: OkHttpClient,
    moshi : Moshi,
    dispatcher : CoroutineDispatcher
) : BaseApiCall(moshi,dispatcher) {
    private val flightStatusService : FlightStatusService = Retrofit.Builder()
        .baseUrl(baseUrl + "v2/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(httpClient)
        .build()
        .create()

    suspend fun get(
        carrierCode : String,
        flightNumber : Int,
        scheduledDepartureDate : String,
        operationalSuffix : String? = null
    ) = baseApiCall {
        flightStatusService.getFlightStatus(
            carrierCode = carrierCode,
            flightNumber = flightNumber,
            scheduledDepartureDate = scheduledDepartureDate,
            operationalSuffix = operationalSuffix
        )
    }
}