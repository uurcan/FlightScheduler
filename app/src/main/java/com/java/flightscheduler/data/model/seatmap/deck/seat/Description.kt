package com.java.flightscheduler.data.model.seatmap.deck.seat

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Description internal constructor(
    val text : String? = null,
    val lang : String? = null
) : Parcelable