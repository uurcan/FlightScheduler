package com.java.flightscheduler.data.model.status.legs

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AircraftEquipment internal constructor(
    val aircraftType : String? = null
) : Parcelable