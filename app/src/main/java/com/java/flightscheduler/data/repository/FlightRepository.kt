package com.java.flightscheduler.data.repository

import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.remote.services.FlightService
import com.java.flightscheduler.data.remote.request.base.BaseApiCall
import com.java.flightscheduler.di.dispatcher.IoDispatcher
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FlightRepository @Inject constructor(
    moshi: Moshi,
    private val flightService: FlightService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher) : BaseApiCall(moshi,dispatcher){

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
        flightService.getFlightOffers(
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
    fun getQueryErrors(errorResults : List<BaseApiResult.Error.Issue>) : String? {
        var errorMessages : String? = "Following errors found : \n \n"
        errorResults.forEach {
                error ->
            errorMessages += "${error.code} - ${error.detail} \n"
        }
        return errorMessages
    }
}