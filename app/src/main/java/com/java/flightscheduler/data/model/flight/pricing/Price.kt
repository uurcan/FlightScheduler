package com.java.flightscheduler.data.model.flight.pricing

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Price internal constructor(
    val currency: String? = null,
    val total: Double = 0.0,
    val base: Double = 0.0,
    val fees: List<Fee>? = null,
    val grandTotal: Double = 0.0
) : Parcelable