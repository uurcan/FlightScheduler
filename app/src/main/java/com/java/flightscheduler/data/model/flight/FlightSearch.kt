package com.java.flightscheduler.data.model.flight

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class FlightSearch internal constructor(
    val originLocationCode : String,
    val destinationLocationCode : String,
    val departureDate : String,
    val arrivalDate : String,
    val adults : Int,
    val excludedAirlineCodes : String? = null,
    val max : Int? = 0
) : Parcelable
