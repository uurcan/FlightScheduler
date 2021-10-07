package com.java.flightscheduler.data.model.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
class FlightSearch(
    var originLocationCode: String = "",
    var destinationLocationCode: String = "",
    var originLocationCity: String = "",
    var destinationLocationCity: String = "",
    var isRoundTrip: Boolean = true,
    var departureDate: String = "",
    var returnDate: String? = null,
    var adults: Int = 1,
    var children: Int = 0,
    var formattedDepartureDate: String = "",
    var formattedReturnDate: String? = null,
    var audits : Int = 1
) : Parcelable