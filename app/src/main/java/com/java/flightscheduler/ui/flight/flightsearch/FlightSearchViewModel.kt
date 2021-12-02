package com.java.flightscheduler.ui.flight.flightsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.repository.FlightRoutesRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class FlightSearchViewModel @Inject constructor(private val flightRoutesRepository: FlightRoutesRepository) : BaseViewModel() {
    private var flightSearchLiveData: MutableLiveData<FlightSearch>? = MutableLiveData()
    private val validationMessage = MutableLiveData("")

    private val oneWayLiveData = MutableLiveData(false)
    val isOneWay: LiveData<Boolean?> get() = oneWayLiveData

    private val originLiveData = MutableLiveData<Airport>()
    val origin: LiveData<Airport> get() = originLiveData

    private val destinationLiveData = MutableLiveData<Airport>()
    val destination: LiveData<Airport> get() = destinationLiveData

    private val flightDateLiveData = MutableLiveData<String>()
    val flightDate: LiveData<String> get() = flightDateLiveData

    private val returnDateLiveData = MutableLiveData<String>()
    val returnDate: LiveData<String> get() = returnDateLiveData

    private val adultCountLiveData = MutableLiveData(1)
    val adultCount: LiveData<Int> get() = adultCountLiveData

    private val childCountLiveData = MutableLiveData(0)
    val childCount: LiveData<Int> get() = childCountLiveData

    private var iataCodeLiveData: MutableLiveData<List<Airport>>? = MutableLiveData()

    fun performValidation(origin: String, destination: String): MutableLiveData<String> {
        validationMessage.value = ""

        if (origin.isBlank()) {
            validationMessage.value = "Origin can not be blank"
        }

        if (destination.isBlank()) {
            validationMessage.value = "Destination can not be blank"
        }
        return validationMessage
    }

    fun getIATACodes(): MutableLiveData<List<Airport>>? {
        val iataList = flightRoutesRepository.getIataCodes()
        iataCodeLiveData?.postValue(iataList)
        return iataCodeLiveData
    }

    fun onOneWaySelected(result : Boolean) {
        oneWayLiveData.value = result
    }

    fun setFlightSearchLiveData(flightSearch: FlightSearch) {
        flightSearchLiveData?.value = flightSearch
    }

    fun onDateSelected(departureDate: String, returnDate: String) {
        flightDateLiveData.value = departureDate
        returnDateLiveData.value = returnDate
    }

    fun onIncreaseAdultSelected(count: Int?) {
        adultCountLiveData.value = flightRoutesRepository.increaseAdultCount(count)
    }

    fun onDecreaseAdultSelected(count: Int?) {
        adultCountLiveData.value = flightRoutesRepository.decreaseAdultCount(count)
    }

    fun onIncreaseChildSelected(count: Int?) {
        childCountLiveData.value = flightRoutesRepository.increaseChildCount(count)
    }

    fun onDecreaseChildSelected(count: Int?) {
        childCountLiveData.value = flightRoutesRepository.decreaseChildCount(count)
    }
}