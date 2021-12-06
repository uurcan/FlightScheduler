package com.java.flightscheduler.di

import com.java.flightscheduler.data.remote.services.FlightService
import com.java.flightscheduler.data.remote.services.FlightStatusService
import com.java.flightscheduler.data.remote.services.HotelService
import com.java.flightscheduler.data.remote.services.MetricsService
import com.java.flightscheduler.data.remote.services.SeatMapService
import com.java.flightscheduler.data.remote.services.PredictionService
import com.java.flightscheduler.di.flight.FlightModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import retrofit2.Retrofit

@Module
@TestInstallIn(components = [SingletonComponent::class],
    replaces = [FlightModule::class])
object TestFlightModule {
    @Provides
    fun provideMetrics(retrofit: Retrofit): MetricsService = retrofit.create(MetricsService::class.java)

    @Provides
    fun provideHotels(retrofit: Retrofit): HotelService = retrofit.create(HotelService::class.java)

    @Provides
    fun provideFlights(retrofit: Retrofit): FlightService = retrofit.create(FlightService::class.java)

    @Provides
    fun provideFlightStatus(retrofit: Retrofit): FlightStatusService = retrofit.create(FlightStatusService::class.java)

    @Provides
    fun provideSeatMaps(retrofit: Retrofit): SeatMapService = retrofit.create(SeatMapService::class.java)

    @Provides
    fun provideDelayPredictions(retrofit: Retrofit): PredictionService = retrofit.create(PredictionService::class.java)
}