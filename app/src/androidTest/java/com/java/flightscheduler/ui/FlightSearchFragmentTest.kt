package com.java.flightscheduler.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.filters.MediumTest
import com.java.flightscheduler.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchFragment
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchFragmentDirections
import com.java.flightscheduler.ui.home.HomeFragment
import com.java.flightscheduler.ui.home.HomeFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class FlightSearchFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickHomeFragmentOffersButton_navigateToFlightSearchFragment() {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.buttonFlightOffersMain)).perform(click())

        verify(navController).navigate(
            HomeFragmentDirections.actionNavHomeToNavFlightSearch().actionId
        )
    }

    @Test
    fun clickFlightSearchFragmentSubmitButton_navigateToFlightResultsFragment() {
        val navController = mock(NavController::class.java)

        val flightSearch = FlightSearch(
            origin = Airport(
                NAME = "Sabiha Gokcen", IATA = "SAW", CITY = "ISTANBUL"
            ),
            destination = Airport(
                NAME = "Izmir Adnan Menderes", IATA = "ADB", CITY = "Izmir"
            ),
            isRoundTrip = false,
            departureDate = "2021-12-12",
            returnDate = "2021-12-20",
            adults = 1,
            children = 0
        )

        launchFragmentInHiltContainer<FlightSearchFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.btn_flight_search_flights)).perform(click())

        verify(navController).navigate(
            FlightSearchFragmentDirections.actionNavFlightSearchToNavFlightResults(flightSearch).actionId
        )
    }
}