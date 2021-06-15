package com.java.flightscheduler.data.model.flight.pricing

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class PricingOptions internal constructor(
    val includedCheckedBagsOnly: Boolean = false,
    val fareType: List<String>? = null,
    val corporateCodes: List<String>? = null,
    val refundableFare: Boolean = false,
    val noRestrictionFare: Boolean = false,
    val noPenaltyFare: Boolean = false
) : Parcelable