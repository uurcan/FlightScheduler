package com.java.flightscheduler.data.model.flight

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SearchSegment internal constructor(
    val departure: AirportInfo? = null,
    val arrival: AirportInfo? = null,
    val carrierCode: String? = null,
    val number: String? = null,
    val aircraft: Aircraft? = null,
    val duration: String? = null,
    val id: String? = null,
    val numberOfStops: Int = 0,
    val blacklistedInEU: Boolean = false,
    val co2Emissions: List<Co2Emissions>? = null
) : Parcelable