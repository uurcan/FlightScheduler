package com.java.flightscheduler.data.model.metrics

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class ItineraryPriceMetrics internal constructor(
    val type: String? = null,
    val origin: MetricsOrigin? = null,
    val destination: MetricsDestination? = null,
    val departureDate: String? = null,
    val transportType: String? = null,
    val oneWay: Boolean? = false,
    val priceMetrics: List<InnerPriceMetrics>? = null
) : Parcelable