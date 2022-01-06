package com.java.flightscheduler.ui.flightstatus.statusresults

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.java.flightscheduler.R
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchViewModel
import com.java.flightscheduler.ui.flightstatus.statussearch.*
import com.java.flightscheduler.ui.theme.FlightSchedulerTheme
import com.java.flightscheduler.utils.MessageHelper
import com.java.flightscheduler.utils.extension.displayTimePicker
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalComposeUiApi
class FlightStatusFragment : Fragment() {
    private val flightSearchViewModel : FlightSearchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                FlightSchedulerTheme {
                    val airlines = flightSearchViewModel.getAirlines()
                    var text by remember { mutableStateOf("") }
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
                    ) {
                        HeaderText(headerText = "Flight Status")
                        AutoCompleteAirport(
                            airlines = airlines
                        )
                        FlightNumberField(
                            text = text,
                            onFlightNumberChanged = { flightNumber ->
                                text = flightNumber
                            }
                        )
                        FlightCalendarButton(
                            calendarHeaderText = context.getString(R.string.text_flight_date)
                        ) {
                            displayTimePicker(context, startForResult, true)
                        }
                        SearchFlightStatusButton(
                            buttonText = context.getString(R.string.text_flight_search)
                        ) {
                            //todo : fragment navigation with compose..
                        }
                        DisclaimerText(disclaimerText = getString(R.string.text_disclaimer))
                    }
                }
            }
        }
    }
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    initializeDateParser(it)
                }
            }
        }

    private fun initializeDateParser(it: Intent) {
        val departureDate: String = it.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()
        MessageHelper.displaySuccessMessage(view, departureDate)
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun Preview(){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderText(headerText = "Flight Status")
        AutoCompleteAirport(
            airlines = listOf()
        )
        FlightNumberField(
            text="TEST"
        )
        FlightCalendarButton("TEST")
        SearchFlightStatusButton("View Results"){
        }
        DisclaimerText(disclaimerText = "Disclaimer")
    }
}
