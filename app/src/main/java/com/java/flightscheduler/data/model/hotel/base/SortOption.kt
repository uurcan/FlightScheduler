package com.java.flightscheduler.data.model.hotel.base

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SortOption internal constructor(
    val sort: String,
    val code: String
): Parcelable