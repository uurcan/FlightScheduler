package com.java.flightscheduler.ui.hotel.hotelsearch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.hotel.City
import com.java.flightscheduler.data.model.hotel.HotelSearch
import com.java.flightscheduler.data.repository.HotelSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HotelSearchViewModel @Inject constructor(private val hotelSearchRepository: HotelSearchRepository): ViewModel() {
    private var cityLiveData : MutableLiveData<List<City>>? = MutableLiveData()
    private var hotelCountLiveData : MutableLiveData<Int>? = MutableLiveData()
    private var hotelSearchLiveData : MutableLiveData<HotelSearch>? = MutableLiveData()

    private val validationMessage = MutableLiveData("")

    fun performValidation(city : String) : MutableLiveData<String>{
        validationMessage.value = ""

        if (city.isBlank()) {
            validationMessage.value = "City is missing"
        }
        return validationMessage
    }

    fun setHotelSearchLiveData(hotelSearch: HotelSearch){
        hotelSearchLiveData?.value = hotelSearch
    }

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