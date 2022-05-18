package com.java.flightscheduler.data.model.metrics

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Currency internal constructor(
    val currencyCode : String,
    val currencyName: String,
    val currencySign: String
):Parcelable