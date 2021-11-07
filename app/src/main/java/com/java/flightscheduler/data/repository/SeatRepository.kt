package com.java.flightscheduler.data.repository

import com.java.flightscheduler.data.model.seatmap.deck.seat.Coordinates
import com.java.flightscheduler.data.model.seatmap.deck.seat.Seat

class SeatRepository {
    //todo more efficient way is required..
    fun getSeatLayout(seats : List<Seat>) : List<Seat>{
        val alignedSeatList = seats.toMutableList()
        when {
            seats.size % 6 == 0 -> { //medium deck - contains size / 6 aisle
                seats.forEachIndexed {
                        index, element ->
                    if (element.number?.contains("C") != false){
                        alignedSeatList.add(index,Seat())
                    }
                }
            }
            seats.size % 9 == 0 -> { //large deck - contains (size / 9) * 2 aisle
                for (i in 0..alignedSeatList.size) {
                    if (i % 9 == 3 || i % 9 == 6) {
                        alignedSeatList.add(i,Seat("Aisle", "0", null, null, Coordinates(0, 0)))
                    }
                }
            }
        }
        return alignedSeatList
    }
}