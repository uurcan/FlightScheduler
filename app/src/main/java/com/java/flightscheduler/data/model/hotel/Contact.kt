package com.java.flightscheduler.data.model.hotel

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Contact internal constructor(
    val phone : String? = null,
    val fax : String? = null
) : Parcelable