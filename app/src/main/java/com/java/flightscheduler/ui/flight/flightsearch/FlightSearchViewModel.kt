package com.java.flightscheduler.ui.flight.flightsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.remote.repository.FlightRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class FlightSearchViewModel @Inject constructor(private val flightRepository: FlightRepository) : BaseViewModel() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    private var flightLiveData : MutableLiveData<List<FlightOffer>>? = MutableLiveData()
    private var flightSearchLiveData : MutableLiveData<FlightSearch>? = MutableLiveData()

    fun getFlightData(flightSearch : FlightSearch) : MutableLiveData<List<FlightOffer>>?{
        scope.launch {
            val flightOffersSearches = flightRepository.get(
                originLocationCode = flightSearch.originLocationCode,
                destinationLocationCode = flightSearch.destinationLocationCode,
                departureDate = flightSearch.departureDate,
                adults = flightSearch.adults,
                excludedAirlineCodes = flightSearch.excludedAirlineCodes,
                max = flightSearch.max
            )

            if (flightOffersSearches is BaseApiResult.Success) {
                flightLiveData.apply {
                    flightLiveData?.postValue(flightOffersSearches.data)
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