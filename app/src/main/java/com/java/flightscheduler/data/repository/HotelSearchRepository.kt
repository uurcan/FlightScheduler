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
    context : Context
) {
    private var inputStream : InputStream = context.resources.openRawResource(R.raw.city_codes)
    private var bufferedReader: BufferedReader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))

    fun getCities() : List<City>{
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
}