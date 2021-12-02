package com.java.flightscheduler.data.model.hotel.offers.price

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Changes internal constructor(
    val startDate: String? = null,
    val endDate: String? = null,
    val total: Double = 0.0
) : Parcelable
