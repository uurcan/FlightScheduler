package com.java.flightscheduler.ui.flightsearch

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.remote.response.FlightInitializer
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

@HiltViewModel
class FlightSearchViewModel @Inject constructor() : BaseViewModel() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    var flightLiveData : MutableLiveData<List<FlightOffer>>? = null
    lateinit var flightInitializer: FlightInitializer
    var flightTempData : ArrayList<FlightOffer>? = null

    fun getFlightData() : LiveData<List<FlightOffer>>? {
        flightInitializer = FlightInitializer.Builder()
            .setClientId("g0Bxb6Aar7qN0SNg22fGfGZJG0Uy1YWz")
            .setClientSecret("HAWQf0DsgPedZLGo")
            .setLogLevel(HttpLoggingInterceptor.Level.BODY)
            .build()

        scope.launch {
            when (flightInitializer.flightSearch
                .get("MAD","IST","2021-10-10",1)) {
                is BaseApiResult.Success -> {
                    result ->

                }
            }
        }
        return flightLiveData
    }
}