package com.java.flightscheduler.data.model.seatmap.base

import android.os.Parcelable
import com.java.flightscheduler.data.model.seatmap.amenities.InnerSeat
import com.java.flightscheduler.data.model.seatmap.amenities.Power
import com.java.flightscheduler.data.model.seatmap.amenities.WiFi
import com.java.flightscheduler.data.model.seatmap.amenities.Food
import com.java.flightscheduler.data.model.seatmap.amenities.Beverage
import com.java.flightscheduler.data.model.seatmap.amenities.Entertainment
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AircraftCabinAmenities internal constructor(
    val power: Power? = null,
    val seat: InnerSeat? = null,
    val wifi: WiFi? = null,
    val entertainment: List<Entertainment>? = null,
    val food: Food? = null,
    val beverage: Beverage? = null
) : Parcelable