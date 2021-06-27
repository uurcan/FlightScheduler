package com.java.flightscheduler.data.remote.api.services

import com.java.flightscheduler.data.model.seatmap.base.SeatMap
import com.java.flightscheduler.data.remote.request.base.BaseApiResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

@JvmSuppressWildcards
interface SeatMapService {
    @POST("v1/shopping/seatmaps")
    suspend fun getSeatMapFromFlightOffer(
        @Body body : Map<String,Any>
    ) : BaseApiResponse<List<SeatMap>>

    @GET
    suspend fun getSeatMapFromFlightURL(
        @Url url : String
    ) : ResponseBody
}