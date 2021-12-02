package com.java.flightscheduler.data.model.prediction

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class DelayPrediction(
    val id: String? = null,
    val probability: String? = null,
    val result: String? = null,
    val subType: String? = null,
    val type: String? = null
) : Parcelable