package com.java.flightscheduler.data.model.status.points

import android.os.Parcelable
import com.java.flightscheduler.data.model.status.points.Arrival
import com.java.flightscheduler.data.model.status.points.Departure
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Points internal constructor(
    val iataCode : String? = null,
    val departure: Departure? = null,
    val arrival: Arrival? = null
) : Parcelable