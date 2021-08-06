package com.java.flightscheduler.ui.flight.flightroutes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.remote.repository.FlightRoutesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlightRoutesViewModel @Inject constructor(private val flightRoutesRepository: FlightRoutesRepository) : ViewModel(){
    private var iataCodeLiveData : MutableLiveData<List<Airport>>? = MutableLiveData()
    private var passengerCountLiveData : MutableLiveData<Int>? = MutableLiveData()

    fun getIATACodes() : MutableLiveData<List<Airport>>?{
        val iataList = flightRoutesRepository.getIataCodes()
        iataCodeLiveData?.postValue(iataList)
        return iataCodeLiveData
    }

    fun increaseChildCount(currentPassengerCount : Int?) : Int?{
        passengerCountLiveData?.value = flightRoutesRepository.increaseChildCount(currentPassengerCount)
        return passengerCountLiveData?.value
    }

    fun decreaseChildCount(currentPassengerCount : Int?): Int?{
        passengerCountLiveData?.value = flightRoutesRepository.decreaseChildCount(currentPassengerCount)
        return passengerCountLiveData?.value
    }

    fun increaseAdultCount(currentPassengerCount : Int?) : Int?{
        passengerCountLiveData?.value = flightRoutesRepository.increaseAdultCount(currentPassengerCount)
        return passengerCountLiveData?.value
    }

    fun decreaseAdultCount(currentPassengerCount : Int?): Int?{
        passengerCountLiveData?.value = flightRoutesRepository.decreaseAdultCount(currentPassengerCount)
        return passengerCountLiveData?.value
    }
}