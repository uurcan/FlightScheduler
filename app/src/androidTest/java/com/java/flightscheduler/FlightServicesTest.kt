package com.java.flightscheduler

import androidx.navigation.Navigator
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.base.succeeded
import com.java.flightscheduler.data.remote.services.FlightService
import com.java.flightscheduler.data.repository.FlightRepository
import com.java.flightscheduler.data.repository.TokenRepository
import com.java.flightscheduler.utils.TypesAdapterFactory
import com.squareup.moshi.Moshi
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runners.MethodSorters
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FlightServicesTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    @Named("test_flight")
    lateinit var flightService : FlightService

    @Inject
    @Named("test_moshi")
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
        assert(result.succeeded)
        if (result is BaseApiResult.Success) {
            assert(result.succeeded)
        }
    }
}