package com.java.flightscheduler

import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.base.succeeded
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Before
import org.junit.Test
import org.junit.runners.MethodSorters
import retrofit2.Retrofit
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FlightServicesTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var moshi: Moshi

    @Inject
    lateinit var retrofit: Retrofit

    private lateinit var flightServices: FlightServices

    @Before
    fun before() {
        hiltAndroidRule.inject()
        flightServices = FlightServices(moshi, retrofit)
    }

    @Test
    fun getFlightOffers() = runBlocking {
        val result = flightServices.flightRepository.get(
            originLocationCode = "PAR",
            destinationLocationCode = "NCE",
            nonStop = false,
            departureDate = "2021-12-29",
            adults = 1
        )
        if (result is BaseApiResult.Success) {
            assert(result.succeeded)
        }
    }

    @Test
    fun getHotelOffers() = runBlocking {
        val result = flightServices.hotelRepository.get(
            cityCode = "IST",
            checkInDate = "2021-12-12",
            checkOutDate = "2021-12-22",
            adults = 1,
            roomQuantity = 1,
            lang = "EN"
        )
        if (result is BaseApiResult.Success) {
            assert(result.succeeded)
        }
    }

    @Test
    fun getFlightStatusResults() = runBlocking {
        val result = flightServices.flightStatusRepository.get(
            carrierCode = "TK",
            flightNumber = 4,
            scheduledDepartureDate = "2021-12-12",
            operationalSuffix = null
        )
        if (result is BaseApiResult.Success) {
            assert(result.succeeded)
        }
    }

    @Test
    fun getItineraryPriceMetricsResults() = runBlocking {
        val result = flightServices.priceMetricsRepository.get(
            originIataCode = "IST",
            destinationIataCode = "KUL",
            departureDate = "2021-12-12",
            currencyCode = "TRY",
            oneWay = true
        )
        if (result is BaseApiResult.Success) {
            assert(result.succeeded)
        }
    }

    @Test
    fun getDelayPredictionResults() = runBlocking {
        val result = flightServices.predictionRepository.get(
            originLocationCode = "NCE",
            destinationLocationCode = "IST",
            departureDate = "2021-12-12",
            departureTime = "18:20:00",
            arrivalDate = "2020-12-14",
            arrivalTime = "22:15:00",
            aircraftCode = "321",
            carrierCode = "TK",
            flightNumber = "1816",
            duration = "PT31H10M"
        )

        if (result is BaseApiResult.Success) {
            assert(result.succeeded)
        }
    }

    @Test
    fun getSeatMapResults() = runBlocking {
        val offer = flightServices.seatMapRepository.get("https://test.api.amadeus.com/v2/shopping/flight-offers?originLocationCode=IST&destinationLocationCode=NYC&departureDate=2021-12-12&adults=1&max=1")
        val result = flightServices.seatMapRepository.post(offer)
        assert(result.succeeded)
    }
}