package com.java.flightscheduler.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.java.flightscheduler.R
import com.java.flightscheduler.launchFragmentInHiltContainer
import com.java.flightscheduler.ui.home.HomeFragment
import com.java.flightscheduler.ui.home.HomeFragmentDirections
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class HomeFragmentTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var  navController : NavController

    @Before
    fun setup() {
        hiltRule.inject()
        navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<HomeFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun clickHomeFragmentOffersButton_navigateToFlightSearchFragment() {
        onView(withId(R.id.buttonFlightOffersMain)).perform(ViewActions.click())

        verify(navController).navigate(
            HomeFragmentDirections.actionNavHomeToNavFlightSearch().actionId
        )
    }

    @Test
    fun clickHomeFragmentOffersButton_navigateToHotelSearchFragment() {
        onView(withId(R.id.buttonHotelOffersMain)).perform(ViewActions.click())

        verify(navController).navigate(
            HomeFragmentDirections.actionNavHomeToNavHotelSearch().actionId
        )
    }
}