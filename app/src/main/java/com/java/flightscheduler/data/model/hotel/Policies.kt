package com.java.flightscheduler.data.model.hotel

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Policies internal constructor(
    val paymentType : String? = null,
    val cancellation : Cancellation? = null
) : Parcelable