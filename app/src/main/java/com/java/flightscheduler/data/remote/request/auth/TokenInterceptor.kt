package com.java.flightscheduler.data.remote.request.auth

import com.java.flightscheduler.data.constants.HttpConstants
import okhttp3.Interceptor
import okhttp3.Response

internal class TokenInterceptor (private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.token()
        return if (token == null) {
            chain.proceed(chain.request())
        } else {
            val authenticatedRequest = chain.request()
                .newBuilder()
                .addHeader(HttpConstants.AUTH,"$token")
                .build()
            chain.proceed(authenticatedRequest)
        }
    }
}