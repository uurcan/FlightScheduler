package com.java.flightscheduler.ui.hotel.hotelsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.hotel.City
import com.java.flightscheduler.data.model.hotel.HotelSearch
import com.java.flightscheduler.data.model.hotel.base.Language
import com.java.flightscheduler.data.model.hotel.base.SortOption
import com.java.flightscheduler.data.repository.HotelSearchRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HotelSearchViewModel @Inject constructor(private val hotelSearchRepository: HotelSearchRepository) : BaseViewModel() {
    var hotelSearchLiveData: MutableLiveData<HotelSearch>? = MutableLiveData()
    private val validationMessage = MutableLiveData("")

    private val cityLiveData : MutableLiveData<City> = MutableLiveData()
    val city : LiveData<City>  get() = cityLiveData

    private val passengerCountLiveData = MutableLiveData(1)
    val passengerCount: LiveData<Int> get() = passengerCountLiveData

    private val roomCountLiveData = MutableLiveData(1)
    val roomCount: LiveData<Int> get() = roomCountLiveData

    private val checkInLiveData = MutableLiveData<String>(hotelSearchRepository.getToday())
    val checkInDate: LiveData<String> get() = checkInLiveData

    private val checkOutLiveData = MutableLiveData<String>(hotelSearchRepository.getNextDay())
    val checkOutDate: LiveData<String> get() = checkOutLiveData

    private val sortByOptionLiveData = MutableLiveData<SortOption?>()
    val sortByOption : LiveData<SortOption?> get() = sortByOptionLiveData

    private val languageOptionLiveData = MutableLiveData<Language?>()
    val languageOption : LiveData<Language?> get() = languageOptionLiveData


    fun setHotelSearchLiveData(hotelSearch: HotelSearch) {
        hotelSearchLiveData?.value = hotelSearch
    }

    fun onIncreasePassengerClicked(count: Int?) {
        passengerCountLiveData.value = hotelSearchRepository.increaseAuditCount(count)!!
    }

    fun onDecreasePassengerClicked(count: Int?) {
        passengerCountLiveData.value = hotelSearchRepository.decreaseAuditCount(count)!!
    }

    fun onIncreaseRoomClicked(count: Int?) {
        roomCountLiveData.value = hotelSearchRepository.increaseRoomCount(count)!!
    }

    fun onDecreaseRoomClicked(count: Int?) {
        roomCountLiveData.value = hotelSearchRepository.decreaseRoomCount(count)!!
    }

    fun setSortBySelected(sorted: SortOption?) {
        sortByOptionLiveData.value = sorted
    }

    fun setLanguageClicked(language: Language?) {
        languageOptionLiveData.value = language
    }

    fun onCheckInSelected(checkIn: String, checkOut: String) {
        checkInLiveData.value = checkIn
        checkOutLiveData.value = checkOut
    }

    fun setCityLiveData(city: City) {
        cityLiveData.value = city
    }

    fun getCities(): Array<City> {
        return hotelSearchRepository.getCities().toTypedArray()
    }

    fun performValidation(city: LiveData<City>): MutableLiveData<String> {
        validationMessage.value = ""
        if (city.value == null) {
            validationMessage.value = "City cannot be blank"
        }
        return validationMessage
    }
}