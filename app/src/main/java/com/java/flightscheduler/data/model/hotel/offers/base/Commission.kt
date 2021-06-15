package com.java.flightscheduler.data.model.hotel.offers.base

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Commission internal constructor(
    val percentage : Double = 0.0
) : Parcelable