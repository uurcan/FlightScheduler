package com.java.flightscheduler.data.model.hotel.offers.base

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class RateFamily internal constructor(
    val code: String? = null,
    val type: String? = null
) : Parcelable