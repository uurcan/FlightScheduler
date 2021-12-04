package com.java.flightscheduler

import androidx.test.runner.AndroidJUnitRunner
import com.java.flightscheduler.data.remote.services.FlightService
import com.java.flightscheduler.data.repository.TokenRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import javax.inject.Inject

@RunWith(AndroidJUnitRunner::class)
@HiltAndroidTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FlightServicesTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject lateinit var dispatcher: CoroutineDispatcher
    @Inject lateinit var flightService: FlightService
    @Inject lateinit var tokenRepository: TokenRepository

    companion object {
        @Before
        fun before() {

        }
    }

    @Test
    fun `Refresh Access token`() = runBlocking {
        val response = tokenRepository.refreshToken()
        println(response)
    }
}