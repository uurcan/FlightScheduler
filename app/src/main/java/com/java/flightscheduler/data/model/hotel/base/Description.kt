package com.java.flightscheduler.data.model.hotel.base

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Description internal constructor(
    val lang: String? = null,
    val text: String? = null
) : Parcelable