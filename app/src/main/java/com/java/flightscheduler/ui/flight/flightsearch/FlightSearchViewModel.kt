package com.java.flightscheduler.ui.flight.flightsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.repository.FlightRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.toObservable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightSearchViewModel @Inject constructor(private val flightRepository: FlightRepository) : BaseViewModel() {
    private var flightSearchLiveData : MutableLiveData<FlightSearch>? = MutableLiveData()
    private val validationMessage = MutableLiveData("")
    var loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    var errorLiveData : MutableLiveData<String>? = MutableLiveData()
    private var flightLiveData : MutableLiveData<List<FlightOffer>>? = MutableLiveData()

    init {
        loadingLiveData.value = true
    }

    fun performValidation(origin : String, destination : String, departureDate : String) : MutableLiveData<String>{
        validationMessage.value = ""

        if (origin.isBlank()) {
            validationMessage.value = "Origin is missing"
        }

        if (destination.isBlank()) {
            validationMessage.value = "Destination is missing"
        }

        if (departureDate.isBlank()) {
            validationMessage.value = "Flight date is missing"
        }
        return validationMessage
    }

    fun getFlightData(flightSearch: FlightSearch) : MutableLiveData<List<FlightOffer>>?{
        viewModelScope.launch {
            val flightOffersSearches = flightRepository.get(
                originLocationCode = flightSearch.originLocationCode,
                destinationLocationCode = flightSearch.destinationLocationCode,
                departureDate = flightSearch.departureDate,
                returnDate = flightSearch.returnDate,
                adults = flightSearch.adults,
                children = flightSearch.children
            )

            when (flightOffersSearches){
                is BaseApiResult.Success -> {
                    flightLiveData.apply {
                        flightLiveData?.postValue(flightOffersSearches.data.toObservable()
                            .distinct { code -> code.itineraries?.get(0)?.segments?.get(0)?.number }
                            .subscribeOn(Schedulers.io())
                            .toList()
                            .blockingGet())
                    }
                    loadingLiveData.value = false
                }
                is BaseApiResult.Error -> {
                    errorLiveData?.value = flightRepository.getQueryErrors(flightOffersSearches.errors)
                    loadingLiveData.value = false
                }
            }
        }
        return flightLiveData
    }

    fun setFlightSearchLiveData(flightSearch: FlightSearch){
        flightSearchLiveData?.value = flightSearch
    }
    fun getFlightSearchLiveData() : LiveData<FlightSearch>? = flightSearchLiveData
}