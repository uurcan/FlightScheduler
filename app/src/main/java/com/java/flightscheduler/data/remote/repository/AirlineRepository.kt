package com.java.flightscheduler.data.remote.repository

import android.content.Context
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airline
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class AirlineRepository @Inject constructor(
    context : Context
) {
    private var inputStream : InputStream = context.resources.openRawResource(R.raw.airline_codes)
    private var bufferedReader: BufferedReader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))

    fun getAirlines() : List<Airline>{
        val airportList : ArrayList<Airline> = ArrayList()
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
    fun getMatchingAirline(airlines : List<Airline>,carrier : String?) : Airline? {
        return airlines.find { data -> carrier == data.ID }
    }
}