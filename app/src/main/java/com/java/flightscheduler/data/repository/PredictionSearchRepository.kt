package com.java.flightscheduler.data.repository

import android.content.Context
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airline
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class PredictionSearchRepository @Inject constructor(
    context: Context
) : BaseSearchRepository(context) {

    fun getMatchingAirline(airlines: List<Airline>, carrier: String?): Airline? {
        return airlines.find { data -> carrier == data.ID }
    }
}