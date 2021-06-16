package com.java.flightscheduler.ui.itinerarymetrics

import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.metrics.ItineraryPriceMetrics
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
class ItineraryMetricsViewModel @Inject constructor() : BaseViewModel() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)

    var loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    var metricsLiveData : MutableLiveData<List<ItineraryPriceMetrics>> = MutableLiveData()
    lateinit var flightInitializer: FlightInitializer

    fun getMetricsData() : MutableLiveData<List<ItineraryPriceMetrics>>? {
        flightInitializer = FlightInitializer.Builder()
            .setClientId("g0Bxb6Aar7qN0SNg22fGfGZJG0Uy1YWz")
            .setClientSecret("HAWQf0DsgPedZLGo")
            .setLogLevel(HttpLoggingInterceptor.Level.BODY)
            .build()

        scope.launch {
            val itineraryMetricsResults = flightInitializer.priceMetrics.get(
                originIataCode = "MAD",
                destinationIataCode = "CDG",
                departureDate = "2021-03-21",
                currencyCode = "USD",
                oneWay = false
            )
            if (itineraryMetricsResults is BaseApiResult.Success) {
                metricsLiveData.apply {
                    metricsLiveData.value = itineraryMetricsResults.data
                }
            }
        }
        return metricsLiveData
    }
}