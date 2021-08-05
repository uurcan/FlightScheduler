package com.java.flightscheduler.ui.flight.flightroutes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.flight.Airline
import com.java.flightscheduler.data.remote.repository.AirlineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AirlinesViewModel @Inject constructor(private val airlineRepository: AirlineRepository) : ViewModel(){
    private var airlineLiveData : MutableLiveData<List<Airline>>? = MutableLiveData()

    fun getAirlines() : MutableLiveData<List<Airline>>?{
        val airlineList = airlineRepository.getAirlines()
        airlineLiveData?.postValue(airlineList)
        return airlineLiveData
    }
}