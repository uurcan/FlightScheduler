package com.java.flightscheduler.data.model.flight

import android.os.Parcelable
import com.java.flightscheduler.ui.base.autocomplete.AutoCompleteEntity
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Airline constructor(
    val ID: String,
    val ICC: String,
    val NAME: String?,
    val LOGO: String?
) : Parcelable, AutoCompleteEntity {
    override fun filter(query: String): Boolean {
        return ID.lowercase(Locale.ENGLISH).contains(query) ||
                NAME.toString().lowercase(Locale.ENGLISH).contains(query)
    }
}
