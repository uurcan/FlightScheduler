package com.java.flightscheduler.data.remote.response

import com.java.flightscheduler.data.model.auth.AccessToken
import com.java.flightscheduler.data.repository.FlightSearch
import com.java.flightscheduler.data.repository.FlightStatusSearch
import com.java.flightscheduler.data.repository.HotelSearch
import com.java.flightscheduler.data.remote.api.services.TokenService
import com.java.flightscheduler.data.remote.request.auth.TokenAuthenticator
import com.java.flightscheduler.data.remote.request.auth.TokenInterceptor
import com.java.flightscheduler.data.remote.request.auth.TokenProvider
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class FlightInitializer() : TokenProvider{

    private val tokenService : TokenService
    private var accessToken : AccessToken? = null
    private var tokenValidUntil = 0L

    private var clientId : String
    private var clientSecret : String

    val moshi: Moshi = Moshi.Builder().build()

    val tokenClient = OkHttpClient.Builder()
        .connectTimeout(30,TimeUnit.SECONDS)
        .writeTimeout(30,TimeUnit.SECONDS)
        .readTimeout(30,TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .addInterceptor(TokenInterceptor(this))
        .authenticator(TokenAuthenticator(this))
        .build()

    val flightSearch : FlightSearch
    val hotelSearch : HotelSearch
    //val priceMetrics : MetricsRepository
    val flightStatus : FlightStatusSearch

    init {
        val baseUrl = "https://test.api.amadeus.com/"
        clientId = "g0Bxb6Aar7qN0SNg22fGfGZJG0Uy1YWz"
        clientSecret = "HAWQf0DsgPedZLGo"
        val logLevel = HttpLoggingInterceptor.Level.BODY
        val dispatcher = Dispatchers.IO

        flightSearch = FlightSearch(baseUrl,tokenClient,moshi,dispatcher)
        hotelSearch = HotelSearch(baseUrl,tokenClient,moshi,dispatcher)
        //priceMetrics = MetricsRepository(baseUrl,tokenClient,moshi,dispatcher)
        flightStatus = FlightStatusSearch(baseUrl,tokenClient,moshi,dispatcher)

        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = logLevel })

        tokenService = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
    override fun token(): String? = accessToken?.authorization

    override fun isTokenNullOrExpired() = accessToken == null || System.currentTimeMillis() >= tokenValidUntil

    override fun refreshToken(): String? {
        if (isTokenNullOrExpired()) {
            tokenService.getAccessToken(clientId,clientSecret)
                .execute()
                .takeIf { it.isSuccessful && it.body() != null }?.let { response ->
                    response.body()?.let {
                        tokenValidUntil= System.currentTimeMillis() + (it.expiresIn * 1000)
                        accessToken = it
                    }
                }
        }
        return token()
    }
}