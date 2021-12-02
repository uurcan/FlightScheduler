package com.java.flightscheduler.data.model.seatmap.deck.pricing

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class TravelerPricing internal constructor(
    val travelerId: Int? = 0,
    val seatAvailabilityStatus: String? = null,
    val price: Price? = null
) : Parcelable