package com.java.flightscheduler.integration

import com.java.flightscheduler.data.model.seatmap.deck.seat.Coordinates
import com.java.flightscheduler.data.model.seatmap.deck.seat.Seat
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class FlightTest {
    @Test
    fun eq(){
        assert(true)
    }
    @Test
    fun boundIndexTest(){
        val connectionVariables = mutableListOf("Ahmed","Mehmed","Murad","Mustafa","Kemal")

        for (index in 0..connectionVariables.size.minus(1)){
            val result : String = doStuff(connectionVariables[index],connectionVariables[index.plus(1)])
            connectionVariables.add(result)
        }
        println(connectionVariables)
    }

    private fun doStuff(a : String, b : String) : String{
        return  a + b
    }
    @Test
    fun timeDifferenceTest(){
        val firstLegArrivalDate = "2021-09-30T18:30:00"
        val secondLegDepartureDate = "2021-10-01T18:30:00"

        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val parsedFirstLegDate: Date? = format.parse(firstLegArrivalDate)
        val parsedSecondLegDate: Date? = format.parse(secondLegDepartureDate)

        val timeDiff: Long? = parsedSecondLegDate?.time?.minus(parsedFirstLegDate?.time!!)
        val hours = (timeDiff?.div((1000 * 60 * 60)))?.toInt()
        val mins =(timeDiff?.div((1000 * 60 ))?.rem(60))?.toInt()

        println(hours)
        println(mins)
        assert(mins.toString() == "320") { 1 }
    }
}