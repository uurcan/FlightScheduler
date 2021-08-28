package com.java.flightscheduler.data.model.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
class FlightSearch(
    val originLocationCode: String,
    val destinationLocationCode: String,
    val originLocationCity: String,
    val destinationLocationCity: String,
    val departureDate: String,
    val returnDate: String? = null,
    val adults: Int,
    val children: Int? = 0,
    val formattedDepartureDate: String
) : Parcelable