package com.java.flightscheduler.data.model.flight

data class FareDetailsBySegment(
    val brandedFare: String,
    val cabin: String,
    val class_s: String,
    val fareBasis: String,
    val includedCheckedBags: IncludedCheckedBags,
    val segmentId: String
)