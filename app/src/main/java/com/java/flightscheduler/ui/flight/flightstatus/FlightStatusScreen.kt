package com.java.flightscheduler.ui.flight.flightstatus

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FlightStatusScreen(
    viewModel: FlightStatusViewModel = hiltViewModel()
) {
    val state = viewModel.getFlightStatusLiveData()?.value
    Box(modifier = Modifier.fillMaxSize()) {
        state?.get(0)?.flightDesignator.let { flightDesignator ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(20.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${flightDesignator?.carrierCode} - ${flightDesignator?.flightNumber}",
                            style = MaterialTheme.typography.h2,
                            modifier = Modifier.weight(8f)
                        )
                    }
                }
            }
        }
    }
}