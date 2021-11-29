package com.java.flightscheduler.data.model.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Airport constructor(
    val NAME: String? = null,
    val CITY: String? = null,
    val COUNTRY: String? = null,
    val IATA: String = "",
    val ICAO: String? = null,
    val LAT: String? = null,
    val LON: String? = null,
    val TIMEZONE: String? = null
) : Parcelable
