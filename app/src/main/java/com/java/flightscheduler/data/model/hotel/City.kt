package com.java.flightscheduler.data.model.hotel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class City internal constructor(
    var name : String? = null,
    var country : String? = null,
    var code : String? = null
) : Parcelable