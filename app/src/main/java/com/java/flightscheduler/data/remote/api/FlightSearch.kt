package com.java.flightscheduler.data.remote.api

import com.java.flightscheduler.data.remote.api.base.BaseApiCall
import com.java.flightscheduler.data.remote.api.services.FlightServices
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class FlightSearch internal constructor(baseUrl:String,
                                        httpClient: OkHttpClient,
                                        moshi: Moshi,
                                        dispatcher: CoroutineDispatcher) : BaseApiCall(moshi,dispatcher){

    private val api: FlightServices = Retrofit.Builder()
        .baseUrl(baseUrl + "v2/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(httpClient)
        .build()
        .create()

    suspend fun get(
        originLocationCode: String,
        destinationLocationCode: String,
        departureDate: String,
        adults: Int,
        returnDate: String? = null,
        children: Int? = null,
        infants: Int? = null,
        travelClass: String? = null,
        includedAirlineCodes: String? = null,
        excludedAirlineCodes: String? = null,
        nonStop: Boolean? = null,
        currencyCode: String? = null,
        maxPrice: Int? = null,
        max: Int? = null
    ) = baseApiCall {
        api.getFlightOffers(
            originLocationCode,
            destinationLocationCode,
            departureDate,
            adults,
            returnDate,
            children,
            infants,
            travelClass,
            includedAirlineCodes,
            excludedAirlineCodes,
            nonStop,
            currencyCode,
            maxPrice,
            max
        )
    }
}