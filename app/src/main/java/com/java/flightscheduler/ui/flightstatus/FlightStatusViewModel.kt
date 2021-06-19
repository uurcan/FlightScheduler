package com.java.flightscheduler.ui.flightstatus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.status.base.FlightStatus
import com.java.flightscheduler.data.remote.response.FlightInitializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.logging.HttpLoggingInterceptor

class FlightStatusViewModel : ViewModel() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    var loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    private var flightStatusLiveData : MutableLiveData<List<FlightStatus>>? = MutableLiveData()
    private lateinit var flightInitializer: FlightInitializer

    fun getFlightStatusLiveData() : MutableLiveData<List<FlightStatus>>?{
        flightInitializer = FlightInitializer.Builder()
            .setClientId("g0Bxb6Aar7qN0SNg22fGfGZJG0Uy1YWz")
            .setClientSecret("HAWQf0DsgPedZLGo")
            .setLogLevel(HttpLoggingInterceptor.Level.BODY)
            .build()

        scope.launch {
            val flightStatusResults = flightInitializer.flightStatus.get(
                carrierCode = "PR",
                flightNumber = 212,
                scheduledDepartureDate = "2021-06-22"
            )

            if (flightStatusResults is BaseApiResult.Success) {
                flightStatusLiveData.apply {
                    flightStatusLiveData?.value = flightStatusResults.data
                }
            }
        }
        return flightStatusLiveData
    }
}