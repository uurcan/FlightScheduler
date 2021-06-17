package com.java.flightscheduler.data.model.status.points

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Timings internal constructor(
    val qualifier : String? = null,
    val value : String? = null
) : Parcelable