package com.java.flightscheduler.data.model.hotel

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class Guests internal constructor(
    val adults : Int? = null,
    val childAges: List<Int>? = null
) : Parcelable