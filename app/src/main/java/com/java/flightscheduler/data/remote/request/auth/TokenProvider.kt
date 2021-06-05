package com.java.flightscheduler.data.remote.request.auth

interface TokenProvider {
    fun token() : String?
    fun isTokenNullOrExpired(): Boolean
    fun refreshToken(): String?
}