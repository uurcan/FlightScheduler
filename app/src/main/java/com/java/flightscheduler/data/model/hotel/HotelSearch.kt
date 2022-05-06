package com.java.flightscheduler.data.model.hotel

import android.os.Parcelable
import com.java.flightscheduler.data.model.hotel.base.Language
import com.java.flightscheduler.data.model.hotel.base.SortOption
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HotelSearch internal constructor(
    var city: City? = null,
    var checkInDate: String? = null,
    var checkOutDate: String? = null,
    var formattedCheckInDate: String? = null,
    var formattedCheckOutDate: String? = null,
    var passengerCount: Int = 1,
    var roomCount: Int = 1,
    var sortOptions: SortOption? = null,
    var language: Language? = null
) : Parcelable