package com.java.flightscheduler.data.model.hotel.offers.price

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Average internal constructor(
    val base: String? = null
) : Parcelable