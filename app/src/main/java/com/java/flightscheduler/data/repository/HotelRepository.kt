package com.java.flightscheduler.data.repository

import com.java.flightscheduler.data.remote.api.services.HotelService
import com.java.flightscheduler.data.remote.request.base.BaseApiCall
import com.java.flightscheduler.di.dispatcher.IoDispatcher
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class HotelRepository @Inject constructor(
    moshi: Moshi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val hotelService : HotelService
) : BaseApiCall(moshi,dispatcher){

    suspend fun get ( cityCode: String? = null,
                      latitude: Double? = null,
                      longitude: Double? = null,
                      hotelIds: List<String>? = null,
                      checkInDate: String? = null,
                      checkOutDate: String? = null,
                      roomQuantity: Int? = null,
                      adults: Int? = null,
                      childAges: List<Int>? = null,
                      radius: Int? = null,
                      radiusUnit: String? = null,
                      hotelName: String? = null,
                      chains: List<String>? = null,
                      rateCodes: List<String>? = null,
                      amenities: List<String>? = null,
                      ratings: List<Int>? = null,
                      priceRange: String? = null,
                      currency: String? = null,
                      paymentPolicy: String? = null,
                      boardType: String? = null,
                      includeClosed: Boolean? = null,
                      bestRateOnly: Boolean? = null,
                      view: String? = null,
                      sort: String? = null,
                      pageLimit: Int? = null,
                      pageOffset: String? = null,
                      lang: String? = null
    ) = baseApiCall {
        hotelService.getHotelOffers(
            cityCode = cityCode,
            latitude = latitude,
            longitude = longitude,
            adults = adults,
            amenities = amenities,
            bestRateOnly = bestRateOnly,
            chains = chains,
            boardType = boardType,
            checkInDate = checkInDate,
            checkOutDate = checkOutDate,
            childAges = childAges,
            currency = currency,
            hotelIds = hotelIds,
            hotelName = hotelName,
            includeClosed = includeClosed,
            lang = lang,
            pageLimit = pageLimit,
            pageOffset = pageOffset,
            paymentPolicy = paymentPolicy,
            priceRange = priceRange,
            radius = radius,
            radiusUnit = radiusUnit,
            rateCodes = rateCodes,
            ratings = ratings,
            roomQuantity = roomQuantity,
            sort = sort,
            view = view
        )
    }
}