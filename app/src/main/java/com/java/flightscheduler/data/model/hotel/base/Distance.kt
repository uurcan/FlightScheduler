package com.java.flightscheduler.data.model.hotel.base

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Distance internal constructor(
    val distance: Double = 0.0,
    val distanceUnit: String? = null
) : Parcelable