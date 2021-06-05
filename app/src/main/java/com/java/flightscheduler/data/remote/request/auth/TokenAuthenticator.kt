package com.java.flightscheduler.data.remote.request.auth

import com.java.flightscheduler.data.constants.HttpConstants
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

internal class TokenAuthenticator(private val tokenProvider : TokenProvider) : Authenticator{
    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this) {
            if (tokenProvider.isTokenNullOrExpired()) {
                val token = tokenProvider.refreshToken() ?: return null
                return response.request.newBuilder()
                    .removeHeader(HttpConstants.AUTH)
                    .addHeader(HttpConstants.AUTH,token)
                    .build()
            }
        }
        return null
    }
}