package com.java.flightscheduler.data.model.hotel.base

import android.os.Parcelable
import com.java.flightscheduler.data.repository.HotelSearchRepository
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Media internal constructor(
    var uri : String? = null,
    val category : String? = null
) : Parcelable