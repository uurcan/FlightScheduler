package com.java.flightscheduler.data.model.seatmap.deck.seat

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class DeckConfiguration internal constructor(
    val width : Int? = 0,
    val length : Int? = 0,
    val startSeatRow : Int? = 0,
    val endSeatRow : Int? = 0,
    val startWingsX : Int? = 0,
    val endWingsX : Int? = 0,
    val endWingsRow : Int? = 0,
    val exitRowsX : List<Int>? = null
) : Parcelable