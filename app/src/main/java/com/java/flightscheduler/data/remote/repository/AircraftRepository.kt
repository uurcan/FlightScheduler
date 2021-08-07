package com.java.flightscheduler.data.remote.repository

import android.content.Context
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Aircraft
import com.java.flightscheduler.data.model.flight.Airline
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class AircraftRepository @Inject constructor(
    context : Context
) {
    private var inputStream : InputStream = context.resources.openRawResource(R.raw.aircraft_codes)
    private var bufferedReader: BufferedReader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))

    fun getAircraft() : List<Aircraft>{
        val aircraftList : ArrayList<Aircraft> = ArrayList()
        try {
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