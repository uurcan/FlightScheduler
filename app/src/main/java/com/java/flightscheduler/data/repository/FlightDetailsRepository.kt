package com.java.flightscheduler.data.repository

import android.content.Context
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.HttpConstants.SEAT_MAP_REQUEST_FOOTER
import com.java.flightscheduler.data.constants.HttpConstants.SEAT_MAP_REQUEST_HEADER
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.model.flight.pricing.FareDetailsBySegment
import com.java.flightscheduler.utils.extension.merge
import com.squareup.moshi.Moshi
import java.text.SimpleDateFormat
import javax.inject.Inject
import java.util.Locale
import java.util.Date

class FlightDetailsRepository @Inject constructor(private val context: Context) {
    @Inject
    lateinit var moshi: Moshi

    fun getDuration(segment: SearchSegment) = segment.duration?.substring(2)
    fun getClassCode(fareDetailsBySegment: FareDetailsBySegment) = fareDetailsBySegment.segmentClass
    fun getCabinCode(fareDetailsBySegment: FareDetailsBySegment) = fareDetailsBySegment.cabin

    fun getOfferTemplate(segment: SearchSegment): FlightOffer? {
        val offerTemplate: String = context.assets.open("mock_flight_data.json").bufferedReader().use { it.readText() }
        val offer = moshi.adapter(FlightOffer::class.java).fromJson(offerTemplate)
        offer?.apply {
            segment.id = "1"
            offer.itineraries?.get(0)?.segments = listOf(segment)
        }
        return offer
    }

    fun getSeatMapRequestFromFlightOffer(flightOffer: FlightOffer?): String? {
        val adapter = moshi.adapter(Any::class.java)
        val jsonStructure = adapter.toJson(flightOffer)
        return SEAT_MAP_REQUEST_HEADER + jsonStructure + SEAT_MAP_REQUEST_FOOTER
    }

    fun getFareBasis(fareDetailsBySegment: FareDetailsBySegment): String {
        return if (fareDetailsBySegment.fareBasis.equals(fareDetailsBySegment.cabin)) {
            context.getString(R.string.text_non_applicable)
        } else {
            fareDetailsBySegment.fareBasis.toString()
        }
    }

    fun getFormattedFlightDate(segment: SearchSegment): String {
        val departureTime = segment.departure?.at?.substring(11, 16)
        val arrivalTime = segment.arrival?.at?.substring(11, 16)
        val arrivalDate: String? = segment.arrival?.at?.substring(0, 10)
        var formattedDate = arrivalDate

        val parser = SimpleDateFormat(context.getString(R.string.text_date_parser_format), Locale.ENGLISH)
        val formatter = SimpleDateFormat(context.getString(R.string.text_date_formatter_day), Locale.ENGLISH)
        if (arrivalDate != null) {
            formattedDate = formatter.format(parser.parse(arrivalDate) as Date)
        }

        return "$departureTime - $arrivalTime  $formattedDate"
    }

    fun getSegmentDetails(flightOffer: FlightOffer): List<SearchSegment>? {
        val itinerary = flightOffer.itineraries
        return if (itinerary?.size == 1) {
            itinerary[0].segments
        } else {
            val firstSegment = itinerary?.get(0)?.segments
            val secondSegment = itinerary?.get(1)?.segments

            merge(firstSegment, secondSegment)
        }
    }

    fun getConnectionInfo(flightOffer: FlightOffer): List<String> {
        val connectionVariables = mutableListOf<String>()
        val itinerary = flightOffer.itineraries
        if (itinerary?.size == 1) {
            val oneWaySegment = itinerary[0].segments
            if (oneWaySegment?.size == 1) {
                return listOf(context.getString(R.string.text_end_flight))
            } else {
                connectionVariables.addAll(extractSegmentInfo(oneWaySegment, context.getString(R.string.text_end_flight)))
            }
        } else {
            val firstSegmentData = itinerary?.get(0)?.segments
            val secondSegmentData = itinerary?.get(1)?.segments

            if (firstSegmentData?.size == 1) {
                connectionVariables.add(context.getString(R.string.text_return_segments))
            } else {
                connectionVariables.addAll(extractSegmentInfo(firstSegmentData, context.getString(R.string.text_return_segments)))
            }

            if (secondSegmentData?.size == 1) {
                connectionVariables.add(context.getString(R.string.text_end_flight))
            } else {
                connectionVariables.addAll(extractSegmentInfo(secondSegmentData, context.getString(R.string.text_end_flight)))
            }
        }
        return connectionVariables
    }

    private fun extractSegmentInfo(segment: List<SearchSegment>?, segmentStatus: String): List<String> {
        val connectionVariables = mutableListOf<String>()
        for (index in 0..segment?.size!!) {
            if (index >= segment.size.minus(1)) {
                connectionVariables.add(segmentStatus)
                break
            } else {
                val result: String = calculateConnectionTime(
                    segment[index],
                    segment[index.plus(1)]
                )
                connectionVariables.add(result)
            }
        }
        return connectionVariables
    }

    private fun calculateConnectionTime(
        segment: SearchSegment,
        searchSegment: SearchSegment
    ): String {
        val firstLegArrivalDate: String = segment.arrival?.at!!
        val secondLegDepartureDate: String = searchSegment.departure?.at!!

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val parsedFirstLegDate: Date? = format.parse(firstLegArrivalDate)
        val parsedSecondLegDate: Date? = format.parse(secondLegDepartureDate)

        val timeDiff: Long? = parsedSecondLegDate?.time?.minus(parsedFirstLegDate?.time!!)
        val hours = timeDiff?.div((1000 * 60 * 60))?.toInt()
        val minutes = String.format("%02d", timeDiff?.div((1000 * 60))?.rem(60)?.toInt())

        return if (hours == 0) {
            "Connection time : $minutes m"
        } else {
            "Connection time : $hours h $minutes m"
        }
    }

    fun getFareDetails(flightOffer: FlightOffer): List<FareDetailsBySegment>? = flightOffer.travelerPricings?.get(0)?.fareDetailsBySegment

}