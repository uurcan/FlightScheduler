package com.java.flightscheduler.data.model.seatmap.deck.pricing

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Tax internal constructor(
    val amount : Double? = 0.0,
    val code : String? = null
) : Parcelable