package com.java.flightscheduler.data.model.hotel.offers.room

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class TypeEstimated internal constructor(
    val category: String? = null,
    val beds: Int = 0,
    val bedType: String? = null
) : Parcelable
