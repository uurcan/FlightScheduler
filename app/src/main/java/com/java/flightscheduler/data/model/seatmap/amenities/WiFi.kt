package com.java.flightscheduler.data.model.seatmap.amenities

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class WiFi internal constructor(
    val isChargeable : Boolean? = false,
    val wifiCoverage : String? = null
) : Parcelable