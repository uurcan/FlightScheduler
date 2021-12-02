package com.java.flightscheduler.data.model.metrics

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class MetricsOrigin internal constructor(
    val iataCode: String? = null
) : Parcelable