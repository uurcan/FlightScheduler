package com.java.flightscheduler.ui.flight.flightsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.remote.repository.FlightRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightSearchViewModel @Inject constructor(private val flightRepository: FlightRepository) : BaseViewModel() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    var loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    var errorLiveData : MutableLiveData<String>? = MutableLiveData()
    private var flightLiveData : MutableLiveData<List<FlightOffer>>? = MutableLiveData()
    private var flightSearchLiveData : MutableLiveData<FlightSearch>? = MutableLiveData()

    fun getFlightData(flightSearch: FlightSearch) : MutableLiveData<List<FlightOffer>>?{
        loadingLiveData.value = true

        scope.launch {
            val flightOffersSearches = flightRepository.get(
                originLocationCode = flightSearch.originLocationCode,
                destinationLocationCode = flightSearch.destinationLocationCode,
                departureDate = flightSearch.departureDate,
                returnDate = flightSearch.returnDate,
                adults = flightSearch.adults,
                children = flightSearch.children,
                excludedAirlineCodes = flightSearch.excludedAirlineCodes,
                includedAirlineCodes = flightSearch.includedAirlineCodes,
                currencyCode = flightSearch.currencyCode,
                infants = flightSearch.infants,
                max = flightSearch.max,
                nonStop = flightSearch.nonStop
            )

            when (flightOffersSearches){
                is BaseApiResult.Success -> {
                    flightLiveData.apply {
                        flightLiveData?.postValue(flightOffersSearches.data)
                        loadingLiveData.value = false
                    }
                }
                is BaseApiResult.Error -> {
                    //todo : not working as expected.
                    errorLiveData?.value = flightOffersSearches.errors[0].detail
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