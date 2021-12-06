package com.java.flightscheduler

import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.base.succeeded
import com.java.flightscheduler.data.remote.services.FlightService
import com.java.flightscheduler.data.repository.FlightRepository
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runners.MethodSorters
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FlightServicesTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var flightService : FlightService

    @Inject
    lateinit var moshi: Moshi

    private lateinit var flightRepository : FlightRepository

    @Before
    fun before() {
        hiltAndroidRule.inject()
        flightRepository = FlightRepository(moshi, flightService, Dispatchers.IO)
    }

    @Test
    fun getOffersForToday() = runBlocking {
       val result = flightRepository.get(
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
}