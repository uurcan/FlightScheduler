package com.java.flightscheduler.data.repository

import android.content.Context
import com.java.flightscheduler.data.constants.AppConstants
import com.java.flightscheduler.data.constants.AppConstants.ELEVEN_SEAT_DECK
import com.java.flightscheduler.data.constants.AppConstants.FOUR_SEAT_DECK
import com.java.flightscheduler.data.constants.AppConstants.NINE_SEAT_DECK
import com.java.flightscheduler.data.constants.AppConstants.SIX_SEAT_DECK
import com.java.flightscheduler.data.constants.AppConstants.TEN_SEAT_DECK
import com.java.flightscheduler.data.constants.AppConstants.TWELVE_SEAT_DECK
import com.java.flightscheduler.data.model.seatmap.base.Decks
import com.java.flightscheduler.data.model.seatmap.deck.pricing.TravelerPricing
import com.java.flightscheduler.data.model.seatmap.deck.seat.Seat
import javax.inject.Inject

class SeatRepository @Inject constructor(context: Context) : BaseSearchRepository(context){
    private var alignedSeatList = mutableListOf<Seat>()
    private val indexList = mutableListOf<Int>()

    fun getSeatLayout(decks: Decks): List<Seat> {
        alignedSeatList = decks.seats?.toMutableList()!!
        when (decks.deckConfiguration?.width) {
            FOUR_SEAT_DECK -> {
                return alignSeatList(2, null, 5)
            }
            SIX_SEAT_DECK -> {
                return alignSeatList(3, null, 7)
            }
            NINE_SEAT_DECK -> {
                return alignSeatList(3,6,9)
            }
            TEN_SEAT_DECK -> {
                return alignSeatList(3,7,10)
            }
            ELEVEN_SEAT_DECK -> {
                return alignSeatList(3,7,11)
            }
            TWELVE_SEAT_DECK -> {
                return alignSeatList(3,8,12)
            }
        }
        return alignedSeatList
    }

    private fun alignSeatList(startingIndex: Int, secondIndex : Int?, aisleIndex: Int): List<Seat> {
        val extraSeats = when(secondIndex) {
            null -> (alignedSeatList.size / aisleIndex) + 1
            else -> (alignedSeatList.size / (aisleIndex / 2)) + 1
        }

        alignedSeatList.let {
            for (i in startingIndex..it.size.plus(extraSeats) step aisleIndex)
                indexList.add(i)

            if (secondIndex != null) {
                for (i in secondIndex..it.size.plus(extraSeats) step aisleIndex)
                    indexList.add(i)
            }
        }

        indexList.sort()

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

    fun decreaseLegCount(count: Int?): Int? = count?.minus(1)?.coerceAtLeast(AppConstants.MIN_LEG_COUNT)

    fun increaseLegCount(count: Int?): Int? = count?.plus(1 )?.coerceAtMost(AppConstants.MAX_LEG_COUNT)
}