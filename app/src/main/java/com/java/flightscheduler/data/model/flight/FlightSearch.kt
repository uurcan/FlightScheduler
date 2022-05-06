package com.java.flightscheduler.data.model.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
class FlightSearch(
    var origin: Airport? = null,
    var destination: Airport? = null,
    var departureDate: String? = null,
    var returnDate: String? = null,
    var adults: Int? = 1,
    var children: Int? = 0,
    var formattedDepartureDate: String = "",
    var passengers: Int = 1
) : Parcelable