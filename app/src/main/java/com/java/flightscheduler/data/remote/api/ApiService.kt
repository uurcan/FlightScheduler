package com.java.flightscheduler.data.remote.api

import com.java.flightscheduler.data.remote.response.GetFlightListResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    @GET("")
    suspend fun getFlightData(@QueryMap hashMap: HashMap<String,String> = HashMap()) : GetFlightListResponse
}