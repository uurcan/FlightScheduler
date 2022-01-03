package com.java.flightscheduler.data.model.flight

import android.os.Parcelable
import com.java.flightscheduler.ui.base.autocomplete.AutoCompleteEntity
import kotlinx.android.parcel.Parcelize
import java.util.*

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
) : Parcelable, AutoCompleteEntity {
    override fun filter(query: String): Boolean {
        return CITY.toString().lowercase(Locale.ENGLISH).contains(query) ||
                NAME.toString().lowercase(Locale.ENGLISH).contains(query) ||
                IATA.lowercase(Locale.ENGLISH).contains(query)
    }
}
