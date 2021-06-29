package com.java.flightscheduler.data.remote.api.services

import com.java.flightscheduler.data.model.prediction.DelayPrediction
import com.java.flightscheduler.data.remote.request.base.BaseApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface PredictionService {
    @GET("v1/travel/predictions/flight-delay")
    suspend fun getFlightPrediction(
        @Query("originLocationCode") originLocationCode: String,
        @Query("destinationLocationCode") destinationLocationCode: String,
        @Query("departureDate") departureDate: String,
        @Query("departureTime") departureTime : String,
        @Query("arrivalDate") arrivalDate : String,
        @Query("arrivalTime") arrivalTime : String,
        @Query("aircraftCode") aircraftCode : String,
        @Query("carrierCode") carrierCode : String,
        @Query("flightNumber") flightNumber : String,
        @Query("duration") duration: String
    ) : BaseApiResponse<List<DelayPrediction>>
}