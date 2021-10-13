package com.java.flightscheduler.ui.hotel.hotelresults

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.hotel.HotelSearch
import com.java.flightscheduler.data.model.hotel.base.Address
import com.java.flightscheduler.data.model.hotel.base.Distance
import com.java.flightscheduler.data.model.hotel.base.Hotel
import com.java.flightscheduler.data.model.hotel.base.HotelOffer
import com.java.flightscheduler.data.model.hotel.offers.price.Price
import com.java.flightscheduler.data.repository.HotelRepository
import com.java.flightscheduler.data.repository.HotelResultsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelResultsViewModel @Inject constructor(hotelOffer: HotelOffer, private val hotelRepository: HotelRepository?): ViewModel() {

    var loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    private val hotelResultsRepository = HotelResultsRepository(hotelOffer)
    private var hotelLiveData : MutableLiveData<List<HotelOffer>>? = MutableLiveData()
    var errorLiveData : MutableLiveData<String>? = MutableLiveData()

    init {
        loadingLiveData.value = true
    }

    val hotelOffer = MutableLiveData(hotelResultsRepository.getOffer())
    val hotelPrice = MutableLiveData<Price>(hotelResultsRepository.getPriceResults())
    val hotelDistance =  MutableLiveData<Distance>(hotelResultsRepository.getDistance())
    val hotelInfo =  MutableLiveData<Hotel>(hotelResultsRepository.getResults())
    val hotelAddress =  MutableLiveData<Address>(hotelResultsRepository.getHotelAddress())

    fun getHotelData(hotelSearch : HotelSearch) : MutableLiveData<List<HotelOffer>>?{
        viewModelScope.launch {
            val hotelOfferSearches = hotelRepository?.get(
                cityCode = hotelSearch.city,
                checkInDate = hotelSearch.checkInDate,
                checkOutDate = hotelSearch.checkOutDate,
                roomQuantity = hotelSearch.roomCount,
                adults = hotelSearch.auditCount,
                sort = hotelSearch.sortOptions,
                lang = hotelSearch.language
            )
            when (hotelOfferSearches){
                is BaseApiResult.Success -> {
                    hotelLiveData.apply {
                        hotelLiveData?.postValue(hotelOfferSearches.data)
                        loadingLiveData.value = false
                    }
                }
                is BaseApiResult.Error -> {
                    errorLiveData?.value = hotelRepository?.getQueryErrors(hotelOfferSearches.errors)
                    loadingLiveData.value = false
                }
            }
        }
        return hotelLiveData
    }
}