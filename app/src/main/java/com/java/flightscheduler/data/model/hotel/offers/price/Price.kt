package com.java.flightscheduler.data.model.hotel.offers.price

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Price internal constructor(
    val currency : String? = null,
    val total : Double = 0.0,
    val variations : Variations? = null
) : Parcelable