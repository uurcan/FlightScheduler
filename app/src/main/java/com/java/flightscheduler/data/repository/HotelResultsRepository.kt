package com.java.flightscheduler.data.repository

import com.java.flightscheduler.data.model.hotel.base.HotelOffer

class HotelResultsRepository constructor(private val hotelOffer: HotelOffer) {
    fun getOffer() = hotelOffer
    fun getResults() = hotelOffer.hotel
    fun getDistance() = hotelOffer.hotel?.hotelDistance
    fun getPriceResults() = hotelOffer.offers?.get(0)?.price
    fun getHotelAddress() = hotelOffer.hotel?.address
}