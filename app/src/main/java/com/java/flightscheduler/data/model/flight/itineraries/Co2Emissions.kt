package com.java.flightscheduler.data.model.flight.itineraries

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Co2Emissions internal constructor(
    val weight: Int = 0,
    val weightUnit: String? = null,
    val cabin: String? = null
) : Parcelable