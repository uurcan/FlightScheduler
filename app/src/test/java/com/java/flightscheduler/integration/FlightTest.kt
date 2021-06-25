package com.java.flightscheduler.integration

import com.java.flightscheduler.data.remote.response.TokenInitializer
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.BeforeClass
import org.junit.Test

class FlightTest {
    companion object {
        lateinit var tokenInitializer: TokenInitializer
        lateinit var moshi: Moshi

        @BeforeClass
        @JvmStatic
        fun before(){
            tokenInitializer = TokenInitializer.Builder()
                .setClientId("g0Bxb6Aar7qN0SNg22fGfGZJG0Uy1YWz")
                .setClientSecret("HAWQf0DsgPedZLGo")
                .setLogLevel(HttpLoggingInterceptor.Level.BODY)
                .build()

            moshi = Moshi.Builder().build()
        }
    }

    @Test
    fun flightOffersTest() = runBlocking {assert(
            tokenInitializer.flightRepository.get(
                "MAD",
                "MUC",
                "2021-06-22",
                1
            ).succeeded
        )
    }

    @Test
    fun hotelOffersTest() = runBlocking {assert(
            tokenInitializer.hotelSearch.get(
               cityCode = "LON"
            ).succeeded
        )
    }

    @Test
    fun priceMetricsTest() = runBlocking {assert(
            tokenInitializer.priceMetrics.get(
                originIataCode = "MAD",
                destinationIataCode = "CDG",
                departureDate = "2021-03-21",
                currencyCode = "USD",
                oneWay = false
            ).succeeded
        )
    }
    @Test
    fun getFlightStatus() = runBlocking {
        assert(
            tokenInitializer.flightStatus.get(
                carrierCode = "PR",
                flightNumber = 212,
                scheduledDepartureDate = "2021-06-22"
            ).succeeded
        )
    }
}