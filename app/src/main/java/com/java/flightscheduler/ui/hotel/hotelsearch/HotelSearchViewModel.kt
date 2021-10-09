package com.java.flightscheduler.ui.hotel.hotelsearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.hotel.City
import com.java.flightscheduler.data.model.hotel.base.HotelOffer
import com.java.flightscheduler.data.repository.HotelRepository
import com.java.flightscheduler.data.repository.HotelSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelSearchViewModel @Inject constructor(private val hotelSearchRepository: HotelSearchRepository): ViewModel() {
    private var cityLiveData : MutableLiveData<List<City>>? = MutableLiveData()
    private var hotelCountLiveData : MutableLiveData<Int>? = MutableLiveData()

    fun getCities() : MutableLiveData<List<City>>?{
        val iataList = hotelSearchRepository.getCities()
        cityLiveData?.postValue(iataList)
        return cityLiveData
    }

    fun increaseAuditCount(currentPassengerCount : Int?) : Int?{
        hotelCountLiveData?.value = hotelSearchRepository.increaseAuditCount(currentPassengerCount)
        return hotelCountLiveData?.value
    }

    fun decreaseAuditCount(currentPassengerCount : Int?): Int?{
        hotelCountLiveData?.value = hotelSearchRepository.decreaseAuditCount(currentPassengerCount)
        return hotelCountLiveData?.value
    }

    fun increaseRoomCount(currentPassengerCount : Int?) : Int?{
        hotelCountLiveData?.value = hotelSearchRepository.increaseRoomCount(currentPassengerCount)
        return hotelCountLiveData?.value
    }

    fun decreaseRoomCount(currentPassengerCount : Int?): Int?{
        hotelCountLiveData?.value = hotelSearchRepository.decreaseRoomCount(currentPassengerCount)
        return hotelCountLiveData?.value
    }
}