package com.java.flightscheduler.data.model.flight.itineraries

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Aircraft internal constructor(
    val code: String? = null
) : Parcelable
