package com.java.flightscheduler.data.model.seatmap.amenities

import android.os.Parcelable
import com.java.flightscheduler.data.model.seatmap.deck.seat.Description
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Media internal constructor(
    val title: String? = null,
    val href: String? = null,
    val description: Description? = null,
    val mediaType: String? = null
) : Parcelable