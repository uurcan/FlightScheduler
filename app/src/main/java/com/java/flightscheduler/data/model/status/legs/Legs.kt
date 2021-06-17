package com.java.flightscheduler.data.model.status.legs

import android.os.Parcelable
import com.java.flightscheduler.data.model.status.legs.AircraftEquipment
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Legs internal constructor(
    val boardPointIataCode : String? = null,
    val offPointIataCode : String? = null,
    val aircraftEquipment : AircraftEquipment? = null,
    val scheduledLegDuration : String? = null
) : Parcelable