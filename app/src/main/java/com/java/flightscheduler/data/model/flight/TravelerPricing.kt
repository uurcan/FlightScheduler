package com.java.flightscheduler.data.model.flight

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class TravelerPricing internal constructor(
    val travelerId: String? = null,
    val fareOption: String? = null,
    val travelerType: String? = null,
    val price: Price? = null,
    val fareDetailsBySegment: List<FareDetailsBySegment>? = null
) : Parcelable