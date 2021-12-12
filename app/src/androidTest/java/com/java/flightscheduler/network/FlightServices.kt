package com.java.flightscheduler.network

import com.java.flightscheduler.data.remote.services.FlightService
import com.java.flightscheduler.data.remote.services.HotelService
import com.java.flightscheduler.data.remote.services.FlightStatusService
import com.java.flightscheduler.data.remote.services.MetricsService
import com.java.flightscheduler.data.remote.services.PredictionService
import com.java.flightscheduler.data.remote.services.SeatMapService
import com.java.flightscheduler.data.repository.FlightRepository
import com.java.flightscheduler.data.repository.FlightStatusRepository
import com.java.flightscheduler.data.repository.HotelRepository
import com.java.flightscheduler.data.repository.MetricsRepository
import com.java.flightscheduler.data.repository.PredictionRepository
import com.java.flightscheduler.data.repository.SeatMapRepository
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import javax.inject.Inject

class FlightServices @Inject constructor(moshi: Moshi, retrofit: Retrofit) {
    private val flightService = retrofit.create(FlightService::class.java)
    val flightRepository = FlightRepository(moshi, flightService, Dispatchers.IO)

    private val hotelService = retrofit.create(HotelService::class.java)
    val hotelRepository = HotelRepository(moshi, Dispatchers.IO, hotelService)

    private val flightStatusService = retrofit.create(FlightStatusService::class.java)
    val flightStatusRepository = FlightStatusRepository(moshi, flightStatusService, Dispatchers.IO)

    private val priceMetricsService = retrofit.create(MetricsService::class.java)
    val priceMetricsRepository = MetricsRepository(moshi, Dispatchers.IO, priceMetricsService)

    private val predictionService = retrofit.create(PredictionService::class.java)
    val predictionRepository = PredictionRepository(moshi, Dispatchers.IO, predictionService)

    private val seatMapService = retrofit.create(SeatMapService::class.java)
    val seatMapRepository = SeatMapRepository(moshi, Dispatchers.IO, seatMapService)
}