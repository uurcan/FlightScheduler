package com.java.flightscheduler.data.remote.api.services

import com.java.flightscheduler.data.model.status.base.FlightStatus
import com.java.flightscheduler.data.remote.request.base.BaseApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface FlightStatusService {
    @GET("schedule/flights")
    suspend fun getFlightStatus(
        @Query("carrierCode") carrierCode : String,
        @Query("flightNumber") flightNumber : Int,
        @Query("scheduledDepartureDate") scheduledDepartureDate : String,
        @Query("operationalSuffix") operationalSuffix : String?
    ) : BaseApiResponse<List<FlightStatus>>
}