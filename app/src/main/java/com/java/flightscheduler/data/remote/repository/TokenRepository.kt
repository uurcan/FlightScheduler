package com.java.flightscheduler.data.remote.repository

import com.java.flightscheduler.data.model.auth.AccessToken
import com.java.flightscheduler.data.remote.services.TokenService
import com.java.flightscheduler.data.remote.request.auth.TokenAuthenticator
import com.java.flightscheduler.data.remote.request.auth.TokenInterceptor
import com.java.flightscheduler.data.remote.request.auth.TokenProvider
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import com.java.flightscheduler.data.constants.HttpConstants.clientId
import com.java.flightscheduler.data.constants.HttpConstants.clientSecret
import com.java.flightscheduler.data.constants.TimeConstants.HTTP_TIMEOUT
import com.java.flightscheduler.data.constants.TimeConstants.HTTP_VALID_UNTIL
import com.java.flightscheduler.data.constants.TimeConstants.TOKEN_VALIDITY_MILLISECONDS

class TokenRepository : TokenProvider{

    private val tokenService : TokenService
    private var accessToken : AccessToken? = null
    private var tokenValidUntil = HTTP_VALID_UNTIL

    val moshi: Moshi = Moshi.Builder().build()

    val tokenClient = OkHttpClient.Builder()
        .connectTimeout(HTTP_TIMEOUT,TimeUnit.SECONDS)
        .writeTimeout(HTTP_TIMEOUT,TimeUnit.SECONDS)
        .readTimeout(HTTP_TIMEOUT,TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .addInterceptor(TokenInterceptor(this))
        .authenticator(TokenAuthenticator(this))
        .build()

    init {
        val baseUrl = "https://test.api.amadeus.com/"
        val logLevel = HttpLoggingInterceptor.Level.BODY

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
                        tokenValidUntil= System.currentTimeMillis() + (it.expiresIn * TOKEN_VALIDITY_MILLISECONDS)
                        accessToken = it
                    }
                }
        }
        return token()
    }
}