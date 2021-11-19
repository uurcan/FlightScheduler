package com.java.flightscheduler.data.model.seatmap.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class SeatMapSearch(
    var originLocationCode: String = "",
    var destinationLocationCode: String = "",
    var flightDate: String = "",
    var formattedFlightDate: String = "",
    var legs : Int = 1
) : Parcelable