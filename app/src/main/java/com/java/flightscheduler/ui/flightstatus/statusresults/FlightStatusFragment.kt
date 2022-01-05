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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchViewModel
import com.java.flightscheduler.ui.flightstatus.statussearch.AutoCompleteAirport
import com.java.flightscheduler.ui.flightstatus.statussearch.FlightCalendarButton
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
                    val airports = flightSearchViewModel.getIATA()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        AutoCompleteAirport(
                            airports = airports
                        )
                        AutoCompleteAirport(
                            airports = airports
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            FlightCalendarButton {
                                displayTimePicker(context, startForResult, true)
                            }
                        }
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
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        AutoCompleteAirport(airports = listOf())
        AutoCompleteAirport(airports = listOf())
        FlightCalendarButton()
    }
}
