package com.java.flightscheduler.ui.flightstatus.statussearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.ui.base.autocomplete.AutoCompleteField
import com.java.flightscheduler.ui.base.autocomplete.AutoCompleteTextSearchBar

@ExperimentalComposeUiApi
@Composable
fun AutoCompleteAirport(airports: List<Airport>) {
    AutoCompleteField (
        items = airports,
        itemContent = { airport ->
            AirportAutoCompleteItem(airport)
        }
    ) {
        var value by remember { mutableStateOf("") }
        val view = LocalView.current

        onItemSelected { airport ->
            value = "${airport.CITY} (${airport.CITY})"
            filter(value)
            view.clearFocus()
        }

        AutoCompleteTextSearchBar(
            value = value,
            label = "Airport",
            onDoneActionClick = {
                view.clearFocus()
            },
            onClearClick = {
                value = ""
                filter(value)
                view.clearFocus()
            },
            onFocusChanged = {
                isSearching = it.isFocused
            },
            onValueChanged = { query ->
                value = query
                filter(value)
            }
        )
    }
}

@Composable
fun AirportAutoCompleteItem(airport: Airport) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = airport.CITY.toString(), style = MaterialTheme.typography.subtitle2)
    }
}

