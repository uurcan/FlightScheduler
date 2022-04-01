package com.java.flightscheduler.data.model.hotel.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Language internal constructor(
    val lang: String,
    val code: String
): Parcelable