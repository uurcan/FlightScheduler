package com.java.flightscheduler.ui.flightstatus.statussearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.flight.Airline
import com.java.flightscheduler.data.repository.FlightStatusSearchRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlightStatusSearchViewModel @Inject constructor(
    private val flightStatusSearchRepository: FlightStatusSearchRepository
) : BaseViewModel() {
    private val carrierLiveData = MutableLiveData<Airline>()
    val carrier: LiveData<Airline> get() = carrierLiveData

    private val flightNumberLiveData = MutableLiveData<String?>()
    val flightNumber: LiveData<String?> get() = flightNumberLiveData

    private val flightDateLiveData = MutableLiveData<String>(flightStatusSearchRepository.getToday())
    val flightDate: LiveData<String> get() = flightDateLiveData

    fun setFlightNumber(number : String){
        flightNumberLiveData.value = number
    }

    fun getAirlines() : List<Airline> {
        return flightStatusSearchRepository.getAirlines()
    }

    fun setAirline(airline: Airline) {
        carrierLiveData.value = airline
    }

    fun setFlightDate(departureDate: String) {
        flightDateLiveData.value = departureDate
    }
}