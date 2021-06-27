package com.java.flightscheduler.data.model.seatmap.deck.seat

import android.os.Parcelable
import com.java.flightscheduler.data.model.seatmap.deck.seat.Coordinates
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Facilities internal constructor(
    val code : String? = null,
    val column : String? = null,
    val row : Int? = null,
    val position : String? = null,
    val coordinates : Coordinates? = null
) : Parcelable