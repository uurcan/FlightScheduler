package com.java.flightscheduler.data.model.hotel

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class HotelOffer internal constructor(
    val type: String? = null,
    val hotel : Hotel? = null,
    val available : Boolean = false,
    val offers : List<InnerOffer>? = null
) : Parcelable