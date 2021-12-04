package com.java.flightscheduler.integration

import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.base.succeeded
import com.java.flightscheduler.data.repository.FlightRepository
import com.java.flightscheduler.data.repository.TokenRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.runBlocking
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.runners.MethodSorters
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(JUnit4::class)
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Config(application = HiltTestApplication::class)
class FlightServicesTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject lateinit var flightRepository: FlightRepository

    companion object {
        lateinit var tokenRepository: TokenRepository

        @JvmStatic
        @BeforeClass
        fun before() {
            tokenRepository = TokenRepository.Builder()
                .setClientId("test_id")
                .setClientSecret("test_secret")
                .build()

        }
    }

    @Test
    fun `Refresh Access token`() = runBlocking {
        val response = tokenRepository.refreshToken()
        println(response)
    }

    @Test
    fun `Get Flight Offer for Today`() = runBlocking {
        val result = flightRepository.get(
            originLocationCode = "PAR",
            destinationLocationCode = "NCE",
            nonStop = false,
            departureDate = "2021-12-29",
            adults = 1,
            max = 2
        )
        assert(result.succeeded)
        if (result is BaseApiResult.Success) {
            assert(result.succeeded)
        }
    }
}