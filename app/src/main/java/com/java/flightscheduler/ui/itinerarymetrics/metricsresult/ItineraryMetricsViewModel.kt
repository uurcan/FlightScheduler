package com.java.flightscheduler.ui.itinerarymetrics.metricsresult

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.metrics.ItineraryPriceMetrics
import com.java.flightscheduler.data.model.metrics.MetricSearch
import com.java.flightscheduler.data.repository.MetricsRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItineraryMetricsViewModel @Inject constructor(private val metricsRepository: MetricsRepository) : BaseViewModel() {
    var metricsLiveData: MutableLiveData<List<ItineraryPriceMetrics>?>? = MutableLiveData()

    init {
        showLoading()
    }

    fun getMetricsData(metricSearch : MetricSearch): MutableLiveData<List<ItineraryPriceMetrics>?>? {
        viewModelScope.launch {
            val itineraryMetricsResults = metricsRepository.get(
                originIataCode = metricSearch.origin?.IATA,
                destinationIataCode = metricSearch.destination?.IATA,
                departureDate = metricSearch.departureDate,
                currencyCode = metricSearch.currency?.currencyCode,
                oneWay = true
            )

            when (itineraryMetricsResults) {
                is BaseApiResult.Success -> {
                    metricsLiveData.apply {
                        metricsLiveData?.postValue(itineraryMetricsResults.data)
                        hideLoading()
                    }
                }
                is BaseApiResult.Error -> {
                    errorMessage.value = metricsRepository.getQueryErrors(itineraryMetricsResults.errors)
                    hideLoading()
                }
            }
        }
        return metricsLiveData
    }
}