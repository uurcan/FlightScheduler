package com.java.flightscheduler.data.model.flight

data class Data(
    val id: String,
    val instantTicketingRequired: Boolean,
    val itineraries: List<Itinerary>,
    val lastTicketingDate: String,
    val nonHomogeneous: Boolean,
    val numberOfBookableSeats: Int,
    val oneWay: Boolean,
    val price: Price,
    val pricingOptions: PricingOptions,
    val source: String,
    val travelerPricings: List<TravelerPricing>,
    val type: String,
    val validatingAirlineCodes: List<String>
)