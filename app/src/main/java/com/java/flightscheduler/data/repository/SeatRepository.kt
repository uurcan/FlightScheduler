package com.java.flightscheduler.data.repository

import com.java.flightscheduler.data.constants.AppConstants.DECK_LARGE
import com.java.flightscheduler.data.constants.AppConstants.DECK_MEDIUM
import com.java.flightscheduler.data.constants.AppConstants.DECK_SMALL
import com.java.flightscheduler.data.model.seatmap.base.Decks
import com.java.flightscheduler.data.model.seatmap.deck.seat.Seat

class SeatRepository {
    private var alignedSeatList = mutableListOf<Seat>()
    private val indexList = mutableListOf<Int>()

    fun getSeatLayout(decks : Decks) : List<Seat>{
        alignedSeatList = decks.seats?.toMutableList()!!
        when (decks.deckConfiguration?.width) {
            DECK_SMALL -> { //medium deck - contains "(size / 4)" aisle
                return alignSeatList(decks,"B")
            }

            DECK_MEDIUM -> { //medium deck - contains "(size / 6)" aisle
                return alignSeatList(decks,"C")
            }

            DECK_LARGE -> { //medium deck - contains "(size / 9) * 2" aisle
                decks.seats.forEachIndexed { index, element ->
                    if (element.number?.contains("C") != false.or((element.number?.contains("F") != false)))
                        indexList.add(index + 1)
                }
                indexList.sortedDescending().forEach { element ->
                    alignedSeatList.add(element , Seat())
                }
                return alignedSeatList
            }

        }
        return alignedSeatList
    }

    private fun alignSeatList(decks: Decks, character : String) : List<Seat>{
        decks.seats?.forEachIndexed { index, element ->
            if (element.number?.contains(character) != false)
                indexList.add(index + 1)
        }
        indexList.sortedDescending().forEach { element ->
            alignedSeatList.add(element ,Seat())
        }
        return alignedSeatList
    }
}