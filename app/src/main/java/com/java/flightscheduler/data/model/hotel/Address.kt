package com.java.flightscheduler.data.model.hotel

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Address internal constructor(
    val lines : List<String>? = null,
    val postalCode : String? = null,
    val cityName : String? = null,
    val countryCode : String? = null
) : Parcelable