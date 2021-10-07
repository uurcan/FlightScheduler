package com.java.flightscheduler.ui.flight.flightsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlightSearchViewModel @Inject constructor() : BaseViewModel() {
    private var flightSearchLiveData : MutableLiveData<FlightSearch>? = MutableLiveData()

    private val validationMessage = MutableLiveData("")

    fun performValidation(origin : String, destination : String) : MutableLiveData<String>{
        validationMessage.value = ""

        if (origin.isBlank()) {
            validationMessage.value = "Origin is missing"
        }

        if (destination.isBlank()) {
            validationMessage.value = "Destination is missing"
        }
        return validationMessage
    }

    fun setFlightSearchLiveData(flightSearch: FlightSearch){
        flightSearchLiveData?.value = flightSearch
    }
    fun getFlightSearchLiveData() : LiveData<FlightSearch>? = flightSearchLiveData
}