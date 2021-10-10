package com.java.flightscheduler.ui.hotel.hotelresults

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.hotel.base.HotelOffer
import com.java.flightscheduler.data.repository.HotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelResultsViewModel @Inject constructor(private val hotelRepository: HotelRepository): ViewModel() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    var loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    private var hotelLiveData : MutableLiveData<List<HotelOffer>>? = MutableLiveData()

    fun getCityData() : MutableLiveData<List<HotelOffer>>?{
        scope.launch {
            val hotelOfferSearches = hotelRepository.get(
                cityCode = "LON",
            )

            if (hotelOfferSearches is BaseApiResult.Success) {
                hotelLiveData.apply {
                    hotelLiveData?.postValue(hotelOfferSearches.data)
                }
            }
        }
        return hotelLiveData
    }
}