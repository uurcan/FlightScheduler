package com.java.flightscheduler.data.model.seatmap.base

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AvailableSeatsCounters internal constructor(
    val travelerId: Int? = 0,
    val value : Int? = 0
) : Parcelable