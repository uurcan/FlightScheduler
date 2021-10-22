package com.java.flightscheduler.data.repository

import android.content.Context
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.home.Places
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class LocationRepository @Inject constructor(private val context: Context) {
    fun getPlaces() : Places {
        val placesList : ArrayList<Places> = ArrayList()
        try {
            val inputStream : InputStream = context.resources.openRawResource(R.raw.locations)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
            bufferedReader.readLines().forEach {
                val tokens = it.split(",")
                placesList.add(
                    Places(
                        tokens[0].toInt(),
                        tokens[1],
                        tokens[2]
                    )
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return placesList.random()
    }

    fun initializeMockPlaces() : Places {
        val locationList = arrayListOf<Places>()
        locationList.add(Places(R.drawable.abu_dhabi_uae,"UAE","Abu Dhabi"))
        locationList.add(Places(R.drawable.agra_india,"India","Agra"))
        locationList.add(Places(R.drawable.alesund_norway,"Norway","Alesund"))
        locationList.add(Places(R.drawable.athens_greece,"Greece","Athens"))
        return locationList.random()
    }
}
