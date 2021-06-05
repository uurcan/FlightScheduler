package com.java.flightscheduler.data.remote.api.services

import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.remote.request.base.BaseApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface FlightService {
    @GET("shopping/flight-offers")
    suspend fun getFlightOffers(
        @retrofit2.http.Query("originLocationCode") originLocationCode: String,
        @retrofit2.http.Query("destinationLocationCode") destinationLocationCode: String,
        @retrofit2.http.Query("departureDate") departureDate: String,
        @retrofit2.http.Query("adults") adults: Int,
        @retrofit2.http.Query("returnDate") returnDate: String?,
        @retrofit2.http.Query("children") children: Int?,
        @retrofit2.http.Query("infants") infants: Int?,
        @retrofit2.http.Query("travelClass") travelClass: String?,
        @retrofit2.http.Query("includedAirlineCodes") includedAirlineCodes: String?,
        @retrofit2.http.Query("excludedAirlineCodes") excludedAirlineCodes: String?,
        @retrofit2.http.Query("nonStop") nonStop: Boolean?,
        @retrofit2.http.Query("currencyCode") currencyCode: String?,
        @retrofit2.http.Query("maxPrice") maxPrice: Int?,
        @retrofit2.http.Query("max") max: Int?
    ): BaseApiResponse<List<FlightOffer>>
}