package com.java.flightscheduler.ui.flightstatus.statusresults

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchViewModel
import com.java.flightscheduler.ui.flightstatus.statussearch.AutoCompleteAirport
import com.java.flightscheduler.ui.theme.FlightSchedulerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalComposeUiApi
class FlightStatusFragment : Fragment() {
    //private val flightStatusViewModel : FlightStatusViewModel by viewModels()
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
                        AutoCompleteAirport(airports = airports)
                    }
                }
            }
        }
    }
}
