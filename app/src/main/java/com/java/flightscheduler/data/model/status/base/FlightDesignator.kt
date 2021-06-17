package com.java.flightscheduler.data.model.status.base

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class FlightDesignator internal constructor(
    val carrierCode : String? = null,
    val flightNumber: Int? = 0
) : Parcelable