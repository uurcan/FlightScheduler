package com.java.flightscheduler.data.model.seatmap.amenities

import android.os.Parcelable
import com.java.flightscheduler.data.model.seatmap.amenities.Media
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class InnerSeat internal constructor(
    val legSpace : Int? = 0,
    val spaceUnit : String? = null,
    val tilt : String? = null,
    val medias : List<Media>? = null
) : Parcelable