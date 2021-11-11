package com.java.flightscheduler.data.model.seatmap.base

import android.os.Parcelable
import com.java.flightscheduler.data.model.seatmap.deck.seat.DeckConfiguration
import com.java.flightscheduler.data.model.seatmap.deck.seat.Facilities
import com.java.flightscheduler.data.model.seatmap.deck.seat.Seat
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Decks internal constructor(
    val deckType: String? = null,
    val deckConfiguration : DeckConfiguration? = null,
    val facilities : List<Facilities>? = null,
    val seats : List<Seat>? = null
) : Parcelable