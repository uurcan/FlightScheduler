package com.java.flightscheduler.data.remote.services

import com.java.flightscheduler.data.model.metrics.ItineraryPriceMetrics
import com.java.flightscheduler.data.remote.request.base.BaseApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface MetricsService {
    @GET("v1/analytics/itinerary-price-metrics")
    suspend fun getItineraryPriceMetrics(
        @Query("originIataCode") originIataCode: String?,
        @Query("destinationIataCode") destinationIataCode: String?,
        @Query("departureDate") departureDate: String?,
        @Query("currencyCode") currencyCode: String?,
        @Query("oneWay") oneWay: Boolean?
    ): BaseApiResponse<List<ItineraryPriceMetrics>>
}