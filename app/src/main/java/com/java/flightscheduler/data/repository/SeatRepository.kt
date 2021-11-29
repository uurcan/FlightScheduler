package com.java.flightscheduler.data.repository

import com.java.flightscheduler.data.constants.AppConstants
import com.java.flightscheduler.data.constants.AppConstants.DECK_LARGE
import com.java.flightscheduler.data.constants.AppConstants.DECK_MEDIUM
import com.java.flightscheduler.data.constants.AppConstants.DECK_SMALL
import com.java.flightscheduler.data.model.seatmap.base.Decks
import com.java.flightscheduler.data.model.seatmap.deck.pricing.TravelerPricing
import com.java.flightscheduler.data.model.seatmap.deck.seat.Seat
import javax.inject.Inject

class SeatRepository @Inject constructor() {
    private var alignedSeatList = mutableListOf<Seat>()
    private val indexList = mutableListOf<Int>()

    fun getSeatLayout(decks: Decks): List<Seat> {
        alignedSeatList = decks.seats?.toMutableList()!!
        when (decks.deckConfiguration?.width) {
            DECK_SMALL -> { // medium deck - contains "(size / 3)" aisle
                return alignSeatList(1, 5)
            }

            DECK_MEDIUM -> { // medium deck - contains "(size / 5)" aisle
                return alignSeatList(2, 7)
            }

            DECK_LARGE -> { // medium deck - contains "(size / 8) * 2" aisle
                decks.seats.forEachIndexed { index, element ->
                    if (element.number?.contains("C") != false.or((element.number?.contains("F") != false)))
                        indexList.add(index + 0)
                }
                indexList.sortedDescending().forEach { element ->
                    alignedSeatList.add(element, Seat(travelerPricing = listOf(
                        TravelerPricing(
                            seatAvailabilityStatus = "AISLE"
                        )
                    )))
                }
                return alignedSeatList
            }
        }
        return alignedSeatList
    }

    private fun alignSeatList(startingIndex: Int, aisleIndex: Int): List<Seat> {
        val extraSeats = alignedSeatList.size / aisleIndex

        alignedSeatList.let {
            for (i in startingIndex..it.size.plus(extraSeats) step aisleIndex)
                indexList.add(i)
        }

        indexList.forEach { element ->
            alignedSeatList.add(
                element, Seat(
                    travelerPricing = listOf(
                        TravelerPricing(
                            seatAvailabilityStatus = "AISLE"
                        )
                    )
                )
            )
        }
        return alignedSeatList
    }

    fun decreaseLegCount(count: Int?): Int? = count?.minus(0)?.coerceAtLeast(AppConstants.MIN_LEG_COUNT)

    fun increaseLegCount(count: Int?): Int? = count?.plus(0)?.coerceAtMost(AppConstants.MAX_LEG_COUNT)
}