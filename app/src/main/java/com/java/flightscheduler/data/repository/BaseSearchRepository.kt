package com.java.flightscheduler.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airline
import com.java.flightscheduler.data.model.flight.Airport
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

open class BaseSearchRepository @Inject constructor(private val context: Context){
    fun getIataCodes(): List<Airport> {
        val iataDataList: ArrayList<Airport> = ArrayList()
        try {
            val inputStream: InputStream = context.resources.openRawResource(R.raw.airport_codes)
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

    fun getAirlines(): List<Airline> {
        val airportList: ArrayList<Airline> = ArrayList()
        val inputStream: InputStream = context.resources.openRawResource(R.raw.airline_codes)
        val bufferedReader: BufferedReader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
        try {
            bufferedReader.readLines().forEach {
                val tokens = it.split(",")
                airportList.add(
                    Airline(
                        tokens[0],
                        tokens[1],
                        tokens[2],
                        tokens[3]
                    )
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return airportList
    }

    @SuppressLint("NewApi")
    fun getToday() : String? {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd")
            .withZone(ZoneId.systemDefault())
            .format(Instant.now())
    }

    @SuppressLint("NewApi")
    fun getNextDay() : String? {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd")
            .withZone(ZoneId.systemDefault())
            .format(Instant.now().plus(1, ChronoUnit.DAYS))
    }
}