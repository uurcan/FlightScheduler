package com.java.flightscheduler.data.model.hotel.offers.base

import android.os.Parcelable
import com.java.flightscheduler.data.model.hotel.offers.policies.Policies
import com.java.flightscheduler.data.model.hotel.offers.price.Price
import com.java.flightscheduler.data.model.hotel.offers.room.Room
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class InnerOffer internal constructor(
    val id: String? = null,
    val checkInDate: String? = null,
    val checkOutDate: String? = null,
    val rateCode: String? = null,
    val rateFamilyEstimated: RateFamily? = null,
    val commission: Commission? = null,
    val room: Room? = null,
    val guests: Guests? = null,
    val price: Price? = null,
    val policies: Policies? = null
) : Parcelable