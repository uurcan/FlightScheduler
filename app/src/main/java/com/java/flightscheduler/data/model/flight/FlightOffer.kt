package com.java.flightscheduler.data.model.flight

import android.os.Parcelable
import com.java.flightscheduler.data.model.flight.itineraries.Itinerary
import com.java.flightscheduler.data.model.flight.pricing.Price
import com.java.flightscheduler.data.model.flight.pricing.PricingOptions
import com.java.flightscheduler.data.model.flight.pricing.TravelerPricing
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize


@Parcelize
@JsonClass(generateAdapter = true)
data class FlightOffer internal constructor(
    val type: String? = null,
    val id: String? = null,
    val source: String? = null,
    val instantTicketingRequired: Boolean = false,
    val nonHomogeneous: Boolean = false,
    val oneWay: Boolean = false,
    val lastTicketingDate: String? = null,
    val numberOfBookableSeats: Int = 0,
    val itineraries: List<Itinerary>? = null,
    val price: Price? = null,
    val pricingOptions: PricingOptions? = null,
    val validatingAirlineCodes: List<String>? = null,
    val travelerPricings: List<TravelerPricing>? = null
) : Parcelable
{
    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = (prime * result + (itineraries?.hashCode() ?: 0))
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        val offer = other as FlightOffer
        return itineraries == offer.itineraries
    }
}