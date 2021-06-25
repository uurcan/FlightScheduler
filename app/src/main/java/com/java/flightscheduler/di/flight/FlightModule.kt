package com.java.flightscheduler.di.flight

import com.java.flightscheduler.data.remote.api.services.FlightService
import com.java.flightscheduler.data.remote.api.services.FlightStatusService
import com.java.flightscheduler.data.remote.api.services.HotelService
import com.java.flightscheduler.data.remote.api.services.MetricsService
import com.java.flightscheduler.data.remote.response.TokenInitializer
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
    fun provideToken() : OkHttpClient = TokenInitializer().tokenClient

    @Singleton
    @Provides
    fun provideMetrics(retrofit: Retrofit) : MetricsService = retrofit.create(MetricsService::class.java)

    @Singleton
    @Provides
    fun provideHotels(retrofit: Retrofit) : HotelService = retrofit.create(HotelService::class.java)

    @Singleton
    @Provides
    fun provideFlights(retrofit: Retrofit) : FlightService = retrofit.create(FlightService::class.java)

    @Singleton
    @Provides
    fun provideFlightStatus(retrofit: Retrofit) : FlightStatusService = retrofit.create(FlightStatusService::class.java)
}