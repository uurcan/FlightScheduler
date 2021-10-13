package com.java.flightscheduler.data.repository

import android.content.Context
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants
import com.java.flightscheduler.data.model.hotel.City
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import javax.inject.Inject


class HotelSearchRepository @Inject constructor(
    private val context : Context
) {
    private lateinit var bufferedReader: BufferedReader

    fun getCities() : List<City>{
        val inputStream : InputStream = context.resources.openRawResource(R.raw.city_codes)
        bufferedReader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
        val airportList : ArrayList<City> = ArrayList()
        try {
            bufferedReader.readLines().forEach {
                val tokens = it.split(",")
                airportList.add(
                    City(
                        tokens[0],
                        tokens[1],
                        tokens[2]
                    )
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return airportList
    }

    fun decreaseRoomCount(count : Int?) : Int? = count?.minus(1)?.coerceAtLeast(AppConstants.MIN_ROOM_COUNT)

    fun increaseRoomCount(count : Int?) : Int? = count?.plus(1)?.coerceAtMost(AppConstants.MAX_ROOM_COUNT)

    fun decreaseAuditCount(count : Int?) : Int? = count?.minus(1)?.coerceAtLeast(AppConstants.MIN_AUDIT_COUNT)

    fun increaseAuditCount(count : Int?) : Int? = count?.plus(1)?.coerceAtMost(AppConstants.MAX_AUDIT_COUNT)


    fun getHotelImages() : List<String>{
        val iataDataList : ArrayList<String> = ArrayList()
        try {
            val inputStream : InputStream = context.resources.openRawResource(R.raw.hotel_images)
            bufferedReader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))

            bufferedReader.readLines().forEach {
                val tokens = it.split(",")
                iataDataList.add(
                    tokens[0]
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return iataDataList
    }
}