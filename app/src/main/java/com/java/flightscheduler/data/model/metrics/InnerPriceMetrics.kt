package com.java.flightscheduler.data.model.metrics

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class InnerPriceMetrics internal constructor(
    val amount : Double? = 0.0,
    val quartileRanking : String? = null
) : Parcelable