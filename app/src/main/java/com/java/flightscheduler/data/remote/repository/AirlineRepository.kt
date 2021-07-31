package com.java.flightscheduler.data.remote.repository

import android.content.Context
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airlines
import com.java.flightscheduler.data.model.flight.IATACodes
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

    fun getAirlines() : List<Airlines>{
        val airportList : ArrayList<Airlines> = ArrayList()
        try {
            bufferedReader.readLines().forEach {
                val tokens = it.split(",")
                airportList.add(
                    Airlines(
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
}