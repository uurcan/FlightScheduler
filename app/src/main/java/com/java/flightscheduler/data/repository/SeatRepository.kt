package com.java.flightscheduler.data.repository

import com.java.flightscheduler.data.model.seatmap.deck.seat.Seat
//todo, more efficiency !
class SeatRepository {
    fun getSeatLayout(seats : List<Seat>) : List<Seat>{
        val alignedSeatList = seats.toMutableList()
        val indexList = mutableListOf<Int>()
        when {
            seats.size == 249 -> { //medium deck - contains "(size / 9) * 2" aisle
                seats.forEachIndexed { index, element ->
                    if (element.number?.contains("C") != false.or((element.number?.contains("F") != false)))
                        indexList.add(index + 1)
                }
                indexList.sortedDescending().forEach { element ->
                    alignedSeatList.add(element ,Seat())
                }
                return alignedSeatList
            }
            seats.size == 96 -> { //medium deck - contains "(size / 9) * 2" aisle
                seats.forEachIndexed { index, element ->
                    if (element.number?.contains("B") != false)
                        indexList.add(index + 1)
                }
                indexList.sortedDescending().forEach { element ->
                    alignedSeatList.add(element ,Seat())
                }
                return alignedSeatList
            }

            seats.size % 6 == 0 -> { //medium deck - contains "size / 6" aisle
                seats.forEachIndexed { index, element ->
                    if (element.number?.contains("C") != false)
                        indexList.add(index + 1)
                }
                indexList.sortedDescending().forEach { element ->
                    alignedSeatList.add(element ,Seat())
                }
                return alignedSeatList

            }

            seats.size % 4 == 0 -> { //small deck - contains "size / 4" aisle
                seats.forEachIndexed { index, element ->
                    if (element.number?.contains("B") != false)
                        indexList.add(index + 1)
                }
                indexList.sortedDescending().forEach { element ->
                    alignedSeatList.add(element ,Seat())
                }
            }
         }
        return alignedSeatList
    }
}