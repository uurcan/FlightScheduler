package com.java.flightscheduler.data.model.hotel.offers.policies

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Cancellation (
    val deadline : String? = null
) : Parcelable
