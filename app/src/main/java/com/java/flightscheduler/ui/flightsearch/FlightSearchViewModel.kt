package com.java.flightscheduler.ui.flightsearch

import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.remote.repository.FlightRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class FlightSearchViewModel @Inject constructor(private val flightRepository: FlightRepository) : BaseViewModel() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    var loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    private var flightLiveData : MutableLiveData<List<FlightOffer>>? = MutableLiveData()

    fun getFlightData() : MutableLiveData<List<FlightOffer>>?{
        scope.launch {
            val flightOffersSearches = flightRepository.get(
                originLocationCode = "SYD",
                destinationLocationCode = "BKK",
                departureDate = "2021-06-26",
                returnDate = "2021-06-29",
                adults = 2,
                max = 3
            )

            if (flightOffersSearches is BaseApiResult.Success) {
                flightLiveData.apply {
                    flightLiveData?.postValue(flightOffersSearches.data)
                }
            }
        }
        return flightLiveData
    }
}