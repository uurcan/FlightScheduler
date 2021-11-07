package com.java.flightscheduler.data.model.seatmap.deck.seat

import android.os.Parcelable
import com.java.flightscheduler.data.model.seatmap.deck.pricing.TravelerPricing
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Seat internal constructor(
    val cabin : String? = null,
    val number : String? = null,
    val characteristicsCodes : List<String>? = null,
    val travelerPricing: List<TravelerPricing>? = null,
    val coordinates: Coordinates? = null
) : Parcelable