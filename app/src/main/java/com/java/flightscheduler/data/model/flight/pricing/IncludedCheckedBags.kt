package com.java.flightscheduler.data.model.flight.pricing

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class IncludedCheckedBags internal constructor(
    val weight: Int = 0,
    val weightUnit: String? = null
) : Parcelable