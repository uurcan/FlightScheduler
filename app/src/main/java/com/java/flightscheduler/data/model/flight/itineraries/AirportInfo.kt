package com.java.flightscheduler.data.model.flight.itineraries

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AirportInfo internal constructor(
    val iataCode: String? = null,
    val terminal: String? = null,
    val at: String? = null
) : Parcelable
