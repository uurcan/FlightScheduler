package com.java.flightscheduler.data.remote.repository

import android.content.Context
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.model.flight.pricing.FareDetailsBySegment
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class FlightDetailsRepository @Inject constructor(private val context: Context){

    fun getAircraft(segment: SearchSegment) = segment.aircraft?.code
    fun getOrigin(segment: SearchSegment) = segment.departure?.iataCode
    fun getDestination(segment: SearchSegment) = segment.arrival?.iataCode
    fun getFlightCode(segment: SearchSegment) = segment.carrierCode + " - " + segment.number
    fun getDuration(segment: SearchSegment) = segment.duration
    fun getClassCode(fareDetailsBySegment: FareDetailsBySegment) = fareDetailsBySegment.segmentClass
    fun getFareBasis(fareDetailsBySegment: FareDetailsBySegment) = fareDetailsBySegment.fareBasis
    fun getCabinCode(fareDetailsBySegment: FareDetailsBySegment) = fareDetailsBySegment.cabin


    fun getFormattedFlightDate(segment: SearchSegment) : String {
        val departureTime = segment.departure?.at?.substring(11,16)
        val arrivalTime = segment.arrival?.at?.substring(11,16)
        val arrivalDate : String? = segment.arrival?.at?.substring(0,10)

        /*val parser = SimpleDateFormat(getString(R.string.text_date_parser_format), Locale.ENGLISH)
        val formatter = SimpleDateFormat(getString(R.string.text_date_formatter), Locale.ENGLISH)
        val formattedDate : String? = formatter.format(parser.parse(arrivalDate!!)!!)*/
        //todo parse data
        return "$departureTime - $arrivalTime "
    }

    fun getLegCount(flightOffer: FlightOffer) : Int? {
        val itinerary = flightOffer.itineraries
        return if (itinerary?.size == 1){
            itinerary[0].segments?.size
        } else {
            itinerary?.get(0)?.segments?.size?.plus(itinerary[1].segments!!.size)
        }
    }

    fun getSegmentDetails(flightOffer: FlightOffer) : List<SearchSegment>? {
        val itinerary = flightOffer.itineraries
        return if (itinerary?.size == 1){
            itinerary[0].segments
        } else {
            val firstSegment = itinerary?.get(0)?.segments
            val secondSegment = itinerary?.get(1)?.segments

            merge(firstSegment,secondSegment)
        }
    }

    fun getConnectionInfo(flightOffer: FlightOffer) : List<String> {
        val connectionVariables = mutableListOf<String>()
        val itinerary = flightOffer.itineraries
        if (itinerary?.size == 1){
            val oneWaySegment = itinerary[0].segments
            if (oneWaySegment?.size == 1){
                return listOf(context.getString(R.string.text_end_flight))
            } else {
                extractSegmentInfo(oneWaySegment,context.getString(R.string.text_end_flight))
            }
        } else {
            val firstSegmentData = itinerary?.get(0)?.segments
            val secondSegmentData = itinerary?.get(1)?.segments

            if (firstSegmentData?.size == 1) {
                connectionVariables.add(context.getString(R.string.text_return_segments))
            } else {
                connectionVariables.addAll(extractSegmentInfo(firstSegmentData,context.getString(R.string.text_return_segments)))
            }

            if (secondSegmentData?.size == 1) {
                connectionVariables.add(context.getString(R.string.text_end_flight))
            } else {
                connectionVariables.addAll(extractSegmentInfo(secondSegmentData,context.getString(R.string.text_end_flight)))
            }
        }
        return connectionVariables
    }

    private fun extractSegmentInfo(segment: List<SearchSegment>?,segmentStatus : String) : List<String>{
        val connectionVariables = mutableListOf<String>()
        for (index in 0..segment?.size!!){
            if(index >= segment.size.minus(1)) {
                connectionVariables.add(segmentStatus)
                break
            }
            else {
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
        val minutes = String.format("%02d", timeDiff?.div((1000 * 60 ))?.rem(60)?.toInt())
        return "Connection time : $hours h $minutes m"
    }

    fun getFareDetails(flightOffer: FlightOffer) : List<FareDetailsBySegment>?
            = flightOffer.travelerPricings?.get(0)?.fareDetailsBySegment


    private fun <T> merge(first: List<T>?, second: List<T>?): List<T> {
        return first!!.plus(second!!)
    }
}