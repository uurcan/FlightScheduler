package com.java.flightscheduler.data.remote.api.services

import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.remote.request.base.BaseApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface FlightService {
    @GET("shopping/flight-offers")
    suspend fun getFlightOffers(
        @Query("originLocationCode") originLocationCode: String,
        @Query("destinationLocationCode") destinationLocationCode: String,
        @Query("departureDate") departureDate: String,
        @Query("adults") adults: Int,
        @Query("returnDate") returnDate: String?,
        @Query("children") children: Int?,
        @Query("infants") infants: Int?,
        @Query("travelClass") travelClass: String?,
        @Query("includedAirlineCodes") includedAirlineCodes: String?,
        @Query("excludedAirlineCodes") excludedAirlineCodes: String?,
        @Query("nonStop") nonStop: Boolean?,
        @Query("currencyCode") currencyCode: String?,
        @Query("maxPrice") maxPrice: Int?,
        @Query("max") max: Int?
    ): BaseApiResponse<List<FlightOffer>>
}