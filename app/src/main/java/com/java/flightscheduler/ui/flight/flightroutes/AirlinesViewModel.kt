package com.java.flightscheduler.ui.flight.flightroutes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.flight.Airlines
import com.java.flightscheduler.data.remote.repository.AirlineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirlinesViewModel @Inject constructor(private val airlineRepository: AirlineRepository) : ViewModel(){
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private var airlineLiveData : MutableLiveData<List<Airlines>>? = MutableLiveData()

    fun getAirlines() : MutableLiveData<List<Airlines>>?{
        scope.launch {
            val airlineList = airlineRepository.getAirlines()
            airlineLiveData?.postValue(airlineList)
        }
        return airlineLiveData
    }
}