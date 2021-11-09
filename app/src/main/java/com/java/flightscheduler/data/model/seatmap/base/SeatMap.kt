package com.java.flightscheduler.data.model.seatmap.base

import android.os.Parcelable
import com.java.flightscheduler.data.model.seatmap.base.AircraftCabinAmenities
import com.java.flightscheduler.data.model.seatmap.base.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class SeatMap internal constructor(
    val type: String? = null,
    val flightOfferId: String? = null,
    val segmentId: String? = null,
    val carrierCode: String? = null,
    val number: String? = null,
    val aircraft: Aircraft? = null,
    val departure: Departure? = null,
    val arrival: Arrival? = null,
    val decks: List<Decks>? = null,
    val aircraftCabinAmenities: AircraftCabinAmenities? = null,
    val availableSeatsCounters: List<AvailableSeatsCounters>? = null
) : Parcelable