package com.java.flightscheduler.ui

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.launchFragmentInHiltContainer
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchFragment
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchFragmentDirections
import com.java.flightscheduler.ui.home.HomeFragment
import com.java.flightscheduler.ui.home.HomeFragmentDirections
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.*
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

    private lateinit var  navController : NavController

    private lateinit var fragmentActivity : FragmentActivity

    @Before
    fun setup() {
        hiltRule.inject()
        navController = mock(NavController::class.java)
    }

    @Test
    fun clickHomeFragmentOffersButton_navigateToFlightSearchFragment() {
        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.buttonFlightOffersMain)).perform(click())

        verify(navController).navigate(
            HomeFragmentDirections.actionNavHomeToNavFlightSearch().actionId
        )
    }

    @Test
    fun flightSearchOriginDestinationAutoComplete_fillAirportResultsBySelectingTheListItem() {
        launchFragmentInHiltContainer<FlightSearchFragment> {
            Navigation.setViewNavController(requireView(), navController)
            fragmentActivity = activity!!
        }

        onView(withId(R.id.edt_flight_search_origin))
            .perform(clearText(), typeText("Istanbul"), closeSoftKeyboard())

        onView(withText("IST"))
            .inRoot(withDecorView(not(fragmentActivity.window.decorView)))
            .perform(click())

        onView(withId(R.id.edt_flight_search_destination))
            .perform(clearText(), typeText("Izmir"), closeSoftKeyboard())

        onView(withText("ADB"))
            .inRoot(withDecorView(not(fragmentActivity.window.decorView)))
            .perform(click())
    }
    @Test
    fun clickFlightSearchFragmentSubmitButton_navigateToFlightResultsFragment() {
        val flightSearch = FlightSearch(
            origin = Airport(
                NAME = "Sabiha Gokcen", IATA = "SAW", CITY = "ISTANBUL"
            ),
            destination = Airport(
                NAME = "Izmir Adnan Menderes", IATA = "ADB", CITY = "Izmir"
            ),
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
            FlightSearchFragmentDirections.actionNavFlightSearchToNavFlightResults(flightSearch)
        )
    }
}