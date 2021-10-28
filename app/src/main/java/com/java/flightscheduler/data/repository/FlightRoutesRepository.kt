package com.java.flightscheduler.data.repository

import android.content.Context
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants.MAX_ADULT_COUNT
import com.java.flightscheduler.data.constants.AppConstants.MAX_CHILD_COUNT
import com.java.flightscheduler.data.constants.AppConstants.MIN_ADULT_COUNT
import com.java.flightscheduler.data.constants.AppConstants.MIN_CHILD_COUNT
import com.java.flightscheduler.data.model.flight.*
import com.java.flightscheduler.utils.ParsingUtils
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import kotlin.collections.ArrayList

class FlightRoutesRepository @Inject constructor(
    private val context : Context
) {
    fun getMatchingFlightRoute(iata: List<Airport>, segment: String?): String {
        return iata.find { value -> segment == value.IATA }?.CITY.toString()
    }

    fun getFlightInfo(carrier: Airline?, origin: String, destination: String): FlightInfo {
        return FlightInfo(carrier, ParsingUtils.crop(origin), ParsingUtils.crop(destination))
    }

    fun getMatchingAirport(airportList : List<Airport>,iata : String): String? {
        return airportList.find { port -> iata == port.IATA }?.CITY.toString()
    }

    fun getMatchingAircraft(aircraftList : List<Aircraft>, aircraftCode : String): String? {
        return aircraftList.find { aircraft -> aircraftCode == aircraft.iata }?.name
    }

    fun decreaseAdultCount(count : Int?) : Int? = count?.minus(1)?.coerceAtLeast(MIN_ADULT_COUNT)

    fun increaseAdultCount(count : Int?) : Int? = count?.plus(1)?.coerceAtMost(MAX_ADULT_COUNT)

    fun decreaseChildCount(count : Int?) : Int? = count?.minus(1)?.coerceAtLeast(MIN_CHILD_COUNT)

    fun increaseChildCount(count : Int?) : Int? = count?.plus(1)?.coerceAtMost(MAX_CHILD_COUNT)

    fun getIataCodes() : List<Airport>{
        val iataDataList : ArrayList<Airport> = ArrayList()
        try {
            val inputStream : InputStream = context.resources.openRawResource(R.raw.airport_codes)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))

            bufferedReader.readLines().forEach {
                val tokens = it.split(",")
                iataDataList.add(
                    Airport(
                        tokens[0],
                        tokens[1],
                        tokens[2],
                        tokens[3],
                        tokens[4],
                        tokens[5],
                        tokens[6],
                        tokens[7]
                    )
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return iataDataList
    }

    fun getAircraft() : List<Aircraft>{
        val aircraftList : ArrayList<Aircraft> = ArrayList()
        try {
            val inputStream : InputStream = context.resources.openRawResource(R.raw.aircraft_codes)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
            bufferedReader.readLines().forEach {
                val tokens = it.split(",")
                aircraftList.add(
                    Aircraft(
                        tokens[0],
                        tokens[1]
                    )
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return aircraftList
    }
}