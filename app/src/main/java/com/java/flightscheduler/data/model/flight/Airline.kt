package com.java.flightscheduler.data.model.flight

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Airline constructor(
    val ID: String,
    val ICC: String,
    val NAME: String?,
    val LOGO: String?
) : Parcelable
