package com.java.flightscheduler.data.model.flight.itineraries

import android.os.Parcelable
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class Itinerary internal constructor(
    val duration: String? = null,
    val segments: List<SearchSegment>? = null
): Parcelable