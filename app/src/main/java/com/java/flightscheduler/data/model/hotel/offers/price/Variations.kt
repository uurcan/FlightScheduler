package com.java.flightscheduler.data.model.hotel.offers.price

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Variations internal constructor(
    val average: Average? = null,
    val changes: List<Changes>? = null
) : Parcelable
