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

    fun setFlightSearchLiveData(flightSearch: FlightSearch){
        flightSearchLiveData?.value = flightSearch
    }
    fun getFlightSearchLiveData() : LiveData<FlightSearch>? = flightSearchLiveData
}