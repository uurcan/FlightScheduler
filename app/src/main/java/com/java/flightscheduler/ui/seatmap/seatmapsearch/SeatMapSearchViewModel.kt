package com.java.flightscheduler.ui.seatmap.seatmapsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.hotel.City
import com.java.flightscheduler.data.repository.SeatRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SeatMapSearchViewModel @Inject constructor(private val seatMapRepository: SeatRepository) : BaseViewModel() {
    private val validationMessage = MutableLiveData("")

    private val originLiveData = MutableLiveData<Airport>()
    val origin: LiveData<Airport> get() = originLiveData

    private val destinationLiveData = MutableLiveData<Airport>()
    val destination: LiveData<Airport> get() = destinationLiveData

    private val legCountLiveData = MutableLiveData(1)
    val legCount: LiveData<Int> get() = legCountLiveData

    private val flightDateLiveData = MutableLiveData<String>(seatMapRepository.getToday())
    val flightDate: LiveData<String> get() = flightDateLiveData

    fun getIATACodes(): Array<Airport>? {
        return seatMapRepository.getIataCodes().toTypedArray()
    }

    fun performValidation(origin: LiveData<Airport>?, destination: LiveData<Airport>?): MutableLiveData<String> {
        validationMessage.value = ""
        if (origin == null) {
            validationMessage.value = "Origin cannot be blank"
        } else if (destination == null) {
            validationMessage.value = "Destination cannot be blank"
        }
        return validationMessage
    }

    fun onIncreaseLegSelected(count: Int?) {
        legCountLiveData.value = seatMapRepository.increaseLegCount(count)
    }

    fun onDecreaseLegSelected(count: Int?) {
        legCountLiveData.value = seatMapRepository.decreaseLegCount(count)
    }

    fun onDateSelected(flightDate: String) {
        flightDateLiveData.value = flightDate
    }

    fun setOriginLiveData(origin: Airport) {
        originLiveData.value = origin
    }

    fun setDestinationLiveData(destination: Airport) {
        destinationLiveData.value = destination
    }
}