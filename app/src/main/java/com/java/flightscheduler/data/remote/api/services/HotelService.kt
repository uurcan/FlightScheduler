package com.java.flightscheduler.data.remote.api.services

import com.java.flightscheduler.data.model.hotel.base.HotelOffer
import com.java.flightscheduler.data.remote.request.base.BaseApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface HotelService {
    @GET("shopping/hotel-offers")
    suspend fun getHotelOffers(
        @Query("cityCode") cityCode: String?,
        @Query("latitude") latitude: Double?,
        @Query("longitude") longitude: Double?,
        @Query("hotelIds")  hotelIds: List<String>?,
        @Query("checkInDate") checkInDate: String?,
        @Query("checkOutDate") checkOutDate: String?,
        @Query("roomQuantity") roomQuantity: Int?,
        @Query("adults") adults: Int?,
        @Query("childAges") childAges: List<Int>?,
        @Query("radius") radius: Int?,
        @Query("radiusUnit") radiusUnit: String?,
        @Query("hotelName") hotelName: String?,
        @Query("chains") chains: List<String>?,
        @Query("rateCodes") rateCodes: List<String>?,
        @Query("amenities") amenities: List<String>?,
        @Query("ratings") ratings: List<Int>?,
        @Query("priceRange") priceRange: String?,
        @Query("currency") currency: String?,
        @Query("paymentPolicy") paymentPolicy: String?,
        @Query("boardType") boardType: String?,
        @Query("includeClosed") includeClosed: Boolean?,
        @Query("bestRateOnly") bestRateOnly: Boolean?,
        @Query("view") view: String?,
        @Query("sort") sort: String?,
        @Query("page[limit]") pageLimit: Int?,
        @Query("page[offset]") pageOffset: String?,
        @Query("lang") lang: String?
    ) : BaseApiResponse<List<HotelOffer>>
}