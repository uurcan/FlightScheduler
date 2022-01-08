package com.java.flightscheduler.ui.flightstatus.statussearch

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airline
import com.java.flightscheduler.ui.base.autocomplete.AutoCompleteField
import com.java.flightscheduler.ui.base.autocomplete.AutoCompleteTextSearchBar
import com.java.flightscheduler.ui.theme.Black
import com.java.flightscheduler.ui.theme.GrayGoogle
import com.java.flightscheduler.ui.theme.RedGoogle
import com.java.flightscheduler.ui.theme.regularFontFamily
import com.java.flightscheduler.utils.ParsingUtils

@Composable
fun HeaderText(
    headerText : String
) {
    Spacer(modifier = Modifier.width(20.dp))
    Text(
        text = headerText,
        fontFamily = regularFontFamily,
        color = Black,
        style = MaterialTheme.typography.h4,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 22.dp, end = 22.dp),
    )
}

@ExperimentalComposeUiApi
@Composable
fun AutoCompleteAirport(
    airlines: List<Airline>,
    onAirlineSelected: (Airline) -> Unit = {},
) {
    AutoCompleteField (
        items = airlines,
        itemContent = { airline ->
            AirportAutoCompleteItem(airline)
        }
    ) {
        var value by remember { mutableStateOf("") }
        val view = LocalView.current

        onItemSelected { airline ->
            value = "${airline.NAME} (${airline.ID})"
            filter(value)
            view.clearFocus()
            onAirlineSelected(airline)
        }

        AutoCompleteTextSearchBar(
            value = value,
            label = "Carrier",
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
fun AirportAutoCompleteItem(airline: Airline) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text ="${airline.NAME} (${airline.ID})",
            style = MaterialTheme.typography.subtitle2,
            fontFamily = regularFontFamily
        )
    }
}

@Composable
fun FlightCalendarButton(
    date: String?,
    context : Context,
    calendarHeaderText : String,
    onDateBarClick: (String) -> Unit = {},
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = calendarHeaderText,
            color = Black,
            fontSize = 12.sp,
            textAlign = TextAlign.Start,
            fontFamily = regularFontFamily
        )
        OutlinedButton(
            onClick = { onDateBarClick("") },
            border = BorderStroke(1.dp, Black),
            modifier = Modifier.fillMaxWidth()
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
                    style = MaterialTheme.typography.subtitle1,
                    modifier = Modifier
                        .padding(end = 8.dp, start = 8.dp),
                    fontFamily = regularFontFamily,
                    text = ParsingUtils.parseDate(context = context, date = date)
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.icon_flight_arrow),
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun FlightNumberField(
    text : String,
    onFlightNumberChanged: (String) -> Unit = {},
) {
    OutlinedTextField(
        singleLine = true,
        value = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        label = {
            Text(
                text = "Flight Number",
                fontFamily = regularFontFamily
            )
        },
        placeholder = {
            Text(
                text = "Ex : 321",
                fontFamily = regularFontFamily
            )
        },
        onValueChange = {
            onFlightNumberChanged(it)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = RedGoogle,
            unfocusedBorderColor = Black
        )
    )
}

@Composable
fun SearchFlightStatusButton(
    buttonText : String,
    onButtonClick: () -> Unit = {},
){
    val mainButtonColor = ButtonDefaults.buttonColors(
        backgroundColor = RedGoogle,
        contentColor = White
    )
    Row {
        Button(
            colors = mainButtonColor,
            onClick = {
                onButtonClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 16.dp, end = 16.dp)) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.subtitle1,
                fontFamily = regularFontFamily
            )
        }
    }
}

@Composable
fun DisclaimerText(
    disclaimerText : String
){
    Text(
        text = disclaimerText,
        fontFamily = regularFontFamily,
        color = GrayGoogle,
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 22.dp, end = 22.dp),
    )
}

