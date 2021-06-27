package com.java.flightscheduler.data.model.seatmap.deck.seat

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Coordinates internal constructor(
    val x : Int? = 0,
    val y : Int? = 0
) : Parcelable