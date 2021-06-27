package com.java.flightscheduler.data.model.seatmap.base

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Operating internal constructor(
    val carrierCode: String? = null
) : Parcelable