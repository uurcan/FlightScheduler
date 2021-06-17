package com.java.flightscheduler.data.remote.response

import com.java.flightscheduler.data.model.auth.AccessToken
import com.java.flightscheduler.data.remote.api.FlightSearch
import com.java.flightscheduler.data.remote.api.FlightStatusSearch
import com.java.flightscheduler.data.remote.api.HotelSearch
import com.java.flightscheduler.data.remote.api.MetricsSearch
import com.java.flightscheduler.data.remote.api.services.TokenService
import com.java.flightscheduler.data.remote.request.auth.TokenAuthenticator
import com.java.flightscheduler.data.remote.request.auth.TokenInterceptor
import com.java.flightscheduler.data.remote.request.auth.TokenProvider
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class FlightInitializer private constructor(
    baseUrl: String,
    private val clientId:String,
    private val clientSecret:String,
    private val logLevel : HttpLoggingInterceptor.Level,
    dispatcher: CoroutineDispatcher
) : TokenProvider{

    private val tokenService : TokenService

    private var accessToken : AccessToken? = null

    private var tokenValidUntil = 0L

    val moshi = Moshi.Builder().build()

    private val tokenClient = OkHttpClient.Builder()
        .connectTimeout(30,TimeUnit.SECONDS)
        .writeTimeout(30,TimeUnit.SECONDS)
        .readTimeout(30,TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply { level = logLevel })
        .addInterceptor(TokenInterceptor(this))
        .authenticator(TokenAuthenticator(this))
        .build()

    val flightSearch : FlightSearch
    val hotelSearch : HotelSearch
    val priceMetrics : MetricsSearch
    val flightStatus : FlightStatusSearch
    init {
        flightSearch = FlightSearch(baseUrl,tokenClient,moshi,dispatcher)
        hotelSearch = HotelSearch(baseUrl,tokenClient,moshi,dispatcher)
        priceMetrics = MetricsSearch(baseUrl,tokenClient,moshi,dispatcher)
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

    class Builder internal constructor() {
        private var hostName : String = Hosts.TEST.value
        private lateinit var clientId: String
        private lateinit var clientSecret : String
        private var logLevel : HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.NONE
        private var dispatcher : CoroutineDispatcher = Dispatchers.IO

        enum class Hosts(val value : String){
            TEST("https://test.api.amadeus.com/")
        }
        fun setClientId(clientId: String) = apply { this.clientId = clientId }
        fun setClientSecret(clientSecret: String) = apply { this.clientSecret = clientSecret }
        fun setLogLevel(logLevel: HttpLoggingInterceptor.Level) = apply { this.logLevel = logLevel }

        fun build() = FlightInitializer(hostName,clientId,clientSecret,logLevel,dispatcher)
    }
}