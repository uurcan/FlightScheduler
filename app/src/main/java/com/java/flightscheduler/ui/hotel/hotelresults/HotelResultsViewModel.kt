package com.java.flightscheduler.ui.hotel.hotelresults

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.hotel.HotelSearch
import com.java.flightscheduler.data.model.hotel.base.HotelOffer
import com.java.flightscheduler.data.repository.HotelRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelResultsViewModel @Inject constructor(private val hotelRepository: HotelRepository?) : BaseViewModel() {
    private var hotelLiveData: MutableLiveData<List<HotelOffer>?>? = MutableLiveData()

    init {
        showLoading()
    }

    fun getHotelData(hotelSearch: HotelSearch): MutableLiveData<List<HotelOffer>?>? {
        viewModelScope.launch {
            val hotelOfferSearches = hotelRepository?.get(
                cityCode = hotelSearch.city?.code,
                checkInDate = hotelSearch.checkInDate,
                checkOutDate = hotelSearch.checkOutDate,
                roomQuantity = hotelSearch.roomCount,
                adults = hotelSearch.passengerCount,
                sort = hotelSearch.sortOptions?.code,
                lang = hotelSearch.language?.code
            )
            when (hotelOfferSearches) {
                is BaseApiResult.Success -> {
                    hotelLiveData.apply {
                        hotelLiveData?.postValue(hotelOfferSearches.data)
                        hideLoading()
                    }
                }
                is BaseApiResult.Error -> {
                    errorMessage.value = hotelRepository?.getQueryErrors(hotelOfferSearches.errors)
                    hideLoading()
                }
            }
        }
        return hotelLiveData
    }
}