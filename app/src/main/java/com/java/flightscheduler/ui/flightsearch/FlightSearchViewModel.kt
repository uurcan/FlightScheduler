package com.java.flightscheduler.ui.flightsearch

import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.remote.response.FlightInitializer
import com.java.flightscheduler.ui.base.BaseViewModel
import com.java.flightscheduler.ui.base.loadmorerefresh.BaseLoadMoreRefreshViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

@HiltViewModel
class FlightSearchViewModel @Inject constructor() : BaseViewModel() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    var loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    var flightLiveData : MutableLiveData<List<FlightOffer>>? = MutableLiveData()
    lateinit var flightInitializer: FlightInitializer

    fun getFlightData() : MutableLiveData<List<FlightOffer>>?{
        flightInitializer = FlightInitializer()

        scope.launch {
            val flightOffersSearches = flightInitializer.flightSearch.get(
                originLocationCode = "SYD",
                destinationLocationCode = "BKK",
                departureDate = "2021-06-22",
                returnDate = "2021-06-29",
                adults = 2,
                max = 3
            )

            if (flightOffersSearches is BaseApiResult.Success) {
                flightLiveData.apply {
                    flightLiveData?.value = flightOffersSearches.data
                }
            }
        }
        return flightLiveData
    }
}