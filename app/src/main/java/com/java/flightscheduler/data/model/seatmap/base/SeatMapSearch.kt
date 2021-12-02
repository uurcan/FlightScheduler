package com.java.flightscheduler.data.model.seatmap.base

import android.os.Parcelable
import com.java.flightscheduler.data.model.flight.Airport
import kotlinx.android.parcel.Parcelize

@Parcelize
class SeatMapSearch(
    var origin: Airport = Airport(),
    var destination: Airport = Airport(),
    var flightDate: String = "",
    var formattedFlightDate: String = "",
    var legs: Int = 1
) : Parcelable