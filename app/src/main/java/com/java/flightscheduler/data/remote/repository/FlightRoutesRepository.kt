package com.java.flightscheduler.data.remote.repository

import com.java.flightscheduler.data.model.flight.IATACodes
import java.io.BufferedReader
import java.io.IOException
import javax.inject.Inject

class FlightRoutesRepository @Inject constructor(
    private val bufferedReader: BufferedReader,
) {
    fun getIataCodes() : List<IATACodes>{
        val iataDataList : ArrayList<IATACodes> = ArrayList()
        try {
            bufferedReader.readLines().forEach {
                val tokens = it.split(",")
                iataDataList.add(
                    IATACodes(
                        tokens[0],
                        tokens[1],
                        tokens[2],
                        tokens[3],
                        tokens[4],
                        tokens[5],
                        tokens[6],
                        tokens[7],
                        tokens[8],
                        tokens[9],
                        tokens[10],
                        tokens[11]
                    )
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return iataDataList
    }
}