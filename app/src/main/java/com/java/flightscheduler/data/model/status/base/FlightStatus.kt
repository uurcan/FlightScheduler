package com.java.flightscheduler.data.model.status.base

import android.os.Parcelable
import com.java.flightscheduler.data.model.status.legs.Legs
import com.java.flightscheduler.data.model.status.points.Points
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class FlightStatus internal constructor(
    val type : String? = null,
    val scheduledDepartureDate : String? = null,
    val flightDesignator: FlightDesignator? = null,
    val flightPoints: List<Points>? = null,
    val segments: List<Segments>? = null,
    val legs: List<Legs>? = null
) : Parcelable