package com.java.flightscheduler.data.model.flight

data class Dictionaries(
    val aircraft: String,
    val carriers: Carriers,
    val currencies: Currencies,
    val locations: Locations
)