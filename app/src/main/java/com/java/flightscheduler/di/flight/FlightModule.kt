package com.java.flightscheduler.di.flight

import com.java.flightscheduler.BuildConfig.API_KEY
import com.java.flightscheduler.BuildConfig.API_SECRET
import com.java.flightscheduler.data.remote.services.FlightService
import com.java.flightscheduler.data.remote.services.FlightStatusService
import com.java.flightscheduler.data.remote.services.HotelService
import com.java.flightscheduler.data.remote.services.MetricsService
import com.java.flightscheduler.data.remote.services.SeatMapService
import com.java.flightscheduler.data.remote.services.PredictionService
import com.java.flightscheduler.data.repository.TokenRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FlightModule {
    @Singleton
    @Provides
    fun provideToken(): OkHttpClient = TokenRepository.Builder()
        .setClientId(API_KEY)
        .setClientSecret(API_SECRET)
        .build()
        .tokenClient

    @Singleton
    @Provides
    fun provideMetrics(retrofit: Retrofit): MetricsService = retrofit.create(MetricsService::class.java)

    @Singleton
    @Provides
    fun provideHotels(retrofit: Retrofit): HotelService = retrofit.create(HotelService::class.java)

    @Singleton
    @Provides
    fun provideFlights(retrofit: Retrofit): FlightService = retrofit.create(FlightService::class.java)

    @Singleton
    @Provides
    fun provideFlightStatus(retrofit: Retrofit): FlightStatusService = retrofit.create(
        FlightStatusService::class.java)

    @Singleton
    @Provides
    fun provideSeatMaps(retrofit: Retrofit): SeatMapService = retrofit.create(SeatMapService::class.java)

    @Singleton
    @Provides
    fun provideDelayPredictions(retrofit: Retrofit): PredictionService = retrofit.create(
        PredictionService::class.java)
}