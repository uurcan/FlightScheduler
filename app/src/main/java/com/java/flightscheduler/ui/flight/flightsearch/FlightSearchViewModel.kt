package com.java.flightscheduler.ui.flight.flightsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.repository.FlightSearchRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlightSearchViewModel @Inject constructor(private val flightSearchRepository: FlightSearchRepository) : BaseViewModel() {
    private var flightSearchLiveData: MutableLiveData<FlightSearch>? = MutableLiveData()
    private val validationMessage = MutableLiveData("")

    private val oneWayLiveData = MutableLiveData(false)
    val isOneWay: LiveData<Boolean> get() = oneWayLiveData

    private val originLiveData = MutableLiveData<Airport>()
    val origin: LiveData<Airport> get() = originLiveData

    private val destinationLiveData = MutableLiveData<Airport>()
    val destination: LiveData<Airport> get() = destinationLiveData

    private val flightDateLiveData = MutableLiveData<String>(flightSearchRepository.getToday())
    val flightDate: LiveData<String> get() = flightDateLiveData

    private val returnDateLiveData = MutableLiveData<String>(flightSearchRepository.getNextDay())
    val returnDate: LiveData<String> get() = returnDateLiveData

    private val adultCountLiveData = MutableLiveData(1)
    val adultCount: LiveData<Int> get() = adultCountLiveData

    private val childCountLiveData = MutableLiveData(0)
    val childCount: LiveData<Int> get() = childCountLiveData

    fun performValidation(origin: LiveData<Airport>, destination: LiveData<Airport>): MutableLiveData<String> {
        validationMessage.value = ""

        if (origin.value == null) {
            validationMessage.value = "Origin can not be blank"
        }

        if (destination.value == null) {
            validationMessage.value = "Destination can not be blank"
        }
        return validationMessage
    }

    fun onOneWaySelected(isOneWay : Boolean) {
        oneWayLiveData.value = isOneWay
    }

    fun getIATACodes(): Array<Airport>? {
        return flightSearchRepository.getIataCodes().toTypedArray()
    }

    fun setFlightSearchLiveData(flightSearch: FlightSearch) {
        flightSearchLiveData?.value = flightSearch
    }

    fun onDateSelected(departureDate: String, returnDate: String) {
        flightDateLiveData.value = departureDate
        returnDateLiveData.value = returnDate
    }

    fun setOriginAirport(airport: Airport) {
        originLiveData.value = airport
    }

    fun setDestinationAirport(airport: Airport) {
        destinationLiveData.value = airport
    }

    fun onIncreaseAdultSelected(count: Int?) {
        adultCountLiveData.value = flightSearchRepository.increaseAdultCount(count)
    }

    fun onDecreaseAdultSelected(count: Int?) {
        adultCountLiveData.value = flightSearchRepository.decreaseAdultCount(count)
    }

    fun onIncreaseChildSelected(count: Int?) {
        childCountLiveData.value = flightSearchRepository.increaseChildCount(count)
    }

    fun onDecreaseChildSelected(count: Int?) {
        childCountLiveData.value = flightSearchRepository.decreaseChildCount(count)
    }
}