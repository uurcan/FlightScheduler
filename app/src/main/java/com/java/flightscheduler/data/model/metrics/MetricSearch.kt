package com.java.flightscheduler.data.model.metrics

import android.os.Parcelable
import com.java.flightscheduler.data.model.flight.Airport
import kotlinx.android.parcel.Parcelize
@Parcelize
class MetricSearch(
    var origin: Airport? = null,
    var destination: Airport? = null,
    var departureDate: String? = null,
    var returnDate: String? = null,
    var currency: Currency? = null
) : Parcelable