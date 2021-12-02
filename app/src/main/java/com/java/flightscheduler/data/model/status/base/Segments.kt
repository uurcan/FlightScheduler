package com.java.flightscheduler.data.model.status.base

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Segments internal constructor(
    val boardPointIataCode: String? = null,
    val offPointIataCode: String? = null,
    val scheduledSegmentDuration: String? = null
) : Parcelable