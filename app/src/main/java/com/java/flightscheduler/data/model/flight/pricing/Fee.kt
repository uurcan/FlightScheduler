package com.java.flightscheduler.data.model.flight.pricing

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Fee internal constructor(
    val amount: Double = 0.0,
    val type: String? = null
) : Parcelable