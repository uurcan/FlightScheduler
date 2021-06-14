package com.java.flightscheduler.data.model.hotel

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Room (
    val type : String? = null,
    val typeEstimated : TypeEstimated? = null,
    val description : OfferDescription? = null
) : Parcelable