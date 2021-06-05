package com.java.flightscheduler.data.model.flight

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class FareDetailsBySegment internal constructor(
    val segmentId: String? = null,
    val cabin: String? = null,
    val fareBasis: String? = null,
    @Json(name = "class") @field:Json(name = "class") val segmentClass: String? = null,
    val includedCheckedBags: IncludedCheckedBags? = null
) : Parcelable