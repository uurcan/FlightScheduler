package com.java.flightscheduler.ui.flight.flightroutes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.flight.IATACodes
import com.java.flightscheduler.data.remote.repository.FlightRoutesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightRoutesViewModel @Inject constructor(private val flightRoutesRepository: FlightRoutesRepository) : ViewModel(){
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private var iataCodeLiveData : MutableLiveData<List<IATACodes>>? = MutableLiveData()
    private var passengerCountLiveData : MutableLiveData<Int>? = MutableLiveData()

    fun getIATACodes() : MutableLiveData<List<IATACodes>>?{
        scope.launch {
            val iataList = flightRoutesRepository.getIataCodes()
            iataCodeLiveData?.postValue(iataList)
        }
        return iataCodeLiveData
    }
    fun increaseChildCount(currentPassengerCount : Int?) : Int?{
        val passengerCount = flightRoutesRepository.increaseChildCount(currentPassengerCount)
        passengerCountLiveData?.value = passengerCount
        return passengerCountLiveData?.value
    }
    fun decreaseChildCount(currentPassengerCount : Int?): Int?{
        val passengerCount = flightRoutesRepository.decreaseChildCount(currentPassengerCount)
        passengerCountLiveData?.value = passengerCount
        return passengerCountLiveData?.value
    }
    fun increaseAdultCount(currentPassengerCount : Int?) : Int?{
        val passengerCount = flightRoutesRepository.increaseAdultCount(currentPassengerCount)
        passengerCountLiveData?.value = passengerCount
        return passengerCountLiveData?.value
    }

    fun decreaseAdultCount(currentPassengerCount : Int?): Int?{
        val passengerCount = flightRoutesRepository.decreaseAdultCount(currentPassengerCount)
        passengerCountLiveData?.value = passengerCount
        return passengerCountLiveData?.value
    }
}