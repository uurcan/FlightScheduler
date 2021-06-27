package com.java.flightscheduler.data.model.seatmap.base

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Arrival internal constructor(
    val iataCode: String? = null,
    val terminal: Int? = 0,
    val at : String? = null
) : Parcelable