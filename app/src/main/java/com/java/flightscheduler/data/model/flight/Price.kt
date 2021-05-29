package com.java.flightscheduler.data.model.flight

data class Price(
    val base: String,
    val currency: String,
    val fees: List<Fee>,
    val grandTotal: String,
    val total: String
)