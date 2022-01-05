package com.java.flightscheduler.ui.flightstatus.statussearch

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.java.flightscheduler.R
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
            value = "${airport.CITY} (${airport.IATA})"
            filter(value)
            view.clearFocus()
        }

        AutoCompleteTextSearchBar(
            value = value,
            label = "Origin",
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
        Text(text ="${airport.NAME} (${airport.IATA})", style = MaterialTheme.typography.subtitle2)
    }
}

@Composable
fun FlightCalendarButton(
    onDateBarClick: () -> Unit = {},
){
    OutlinedButton(
        onClick = { onDateBarClick() },
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.icon_flight_calendar) ,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 8.dp)
                .align(alignment = Alignment.CenterVertically)
        )

        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "28 May 2021",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .padding(end = 8.dp, start = 8.dp)
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.icon_flight_arrow) ,
            contentDescription = null,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
