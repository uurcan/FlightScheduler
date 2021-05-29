package com.java.flightscheduler.data.model.flight

data class PricingOptions(
    val fareType: List<String>,
    val includedCheckedBagsOnly: Boolean
)