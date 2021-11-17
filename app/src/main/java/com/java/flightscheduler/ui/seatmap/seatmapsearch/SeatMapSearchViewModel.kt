package com.java.flightscheduler.ui.seatmap.seatmapsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.hotel.HotelSearch
import com.java.flightscheduler.data.repository.SeatRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeatMapSearchViewModel @Inject constructor(private val seatMapRepository: SeatRepository) : BaseViewModel() {
    private val validationMessage = MutableLiveData("")

    private val legCountLiveData = MutableLiveData(1)
    val legCount: LiveData<Int> get() = legCountLiveData

    private val flightDateLiveData = MutableLiveData<String>()
    val flightDate: LiveData<String> get() = flightDateLiveData

    fun performValidation(origin : String, destination : String) : MutableLiveData<String> {
        validationMessage.value = ""
        if (origin.isBlank()) {
            validationMessage.value = "Origin is missing"
        } else if (destination.isBlank()) {
            validationMessage.value = "Destination is missing"
        }
        return validationMessage
    }

    fun onIncreaseLegSelected(count: Int?){
        legCountLiveData.value = seatMapRepository.decreaseLegCount(count)!!
    }

    fun onDecreaseLegSelected(count: Int?){
        legCountLiveData.value = seatMapRepository.increaseLegCount(count)!!
    }

    fun onDateSelected(flightDate : String){
        flightDateLiveData.value = flightDate
    }
}