package com.java.flightscheduler.data.model.prediction

import android.os.Parcelable
import com.java.flightscheduler.data.model.flight.Airline
import com.java.flightscheduler.data.model.flight.Airport
import kotlinx.android.parcel.Parcelize

@Parcelize
class PredictionSearch(
    var origin: Airport? = null,
    var destination: Airport? = null,
    var departureDate: String? = null,
    var departureTime: String? = null,
    var arrivalDate: String? = null,
    var arrivalTime: String? = null,
    var aircraftCode: Int? = 321,
    var carrierCode: Airline? = null,
    var duration: String = "1",
    var flightNumber: Int? = null
) : Parcelable