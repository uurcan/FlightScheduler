package com.java.flightscheduler.data.model.hotel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HotelSearch internal constructor(
    var city: City = City(),
    var checkInDate : String? = null,
    var checkOutDate : String? = null,
    var formattedCheckInDate : String? = null,
    var formattedCheckOutDate : String? = null,
    var auditCount : Int = 1,
    var roomCount : Int = 1,
    var sortOptions : String? = null,
    var language: String? = null
) : Parcelable