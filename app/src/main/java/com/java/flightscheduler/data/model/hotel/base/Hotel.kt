package com.java.flightscheduler.data.model.hotel.base

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Hotel internal constructor(
    val type: String? = null,
    val hotelId : String? = null,
    val chainCode : String? = null,
    val brandCode : String? = null,
    val dupeId : String? = null,
    val name : String? = null,
    val rating : Int? = null,
    val boardType: String? = null,
    val cityCode: String? = null,
    val latitude : Double = 0.0,
    val longitude : Double = 0.0,
    val hotelDistance : Distance? = null,
    val address : Address? = null,
    val contact : Contact? = null,
    val amenities : List<String>? = null,
    val description : Description? = null,
    val media : List<Media>? = null
) : Parcelable