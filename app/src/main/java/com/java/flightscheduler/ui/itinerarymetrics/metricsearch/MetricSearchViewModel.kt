package com.java.flightscheduler.ui.itinerarymetrics.metricsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.metrics.Currency
import com.java.flightscheduler.data.model.metrics.MetricSearch
import com.java.flightscheduler.data.repository.MetricSearchRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MetricSearchViewModel @Inject constructor(private val metricSearchRepository: MetricSearchRepository) : BaseViewModel() {
    var metricSearchLiveData: MutableLiveData<MetricSearch>? = MutableLiveData()
    private val validationMessage = MutableLiveData("")

    private val oneWayLiveData = MutableLiveData(false)
    val isOneWay: LiveData<Boolean> get() = oneWayLiveData

    private val originLiveData = MutableLiveData<Airport>()
    val origin: LiveData<Airport> get() = originLiveData

    private val destinationLiveData = MutableLiveData<Airport>()
    val destination: LiveData<Airport> get() = destinationLiveData

    private val flightDateLiveData = MutableLiveData<String>(metricSearchRepository.getToday())
    val flightDate: LiveData<String> get() = flightDateLiveData

    private val returnDateLiveData = MutableLiveData<String>(metricSearchRepository.getNextDay())
    val returnDate: LiveData<String> get() = returnDateLiveData

    private val currencyLiveData = MutableLiveData<Currency?>()
    val currency : LiveData<Currency?> get() = currencyLiveData


    fun setMetricSearchLiveData(metricSearch: MetricSearch) {
        metricSearchLiveData?.value = metricSearch
    }

    fun setCurrencyClicked(currency: Currency?) {
        currencyLiveData.value = currency
    }

    fun setOriginAirport(airport: Airport) {
        originLiveData.value = airport
    }

    fun setDestinationAirport(airport: Airport) {
        destinationLiveData.value = airport
    }

    fun onOneWaySelected(isOneWay : Boolean) {
        oneWayLiveData.value = isOneWay
    }

    fun getIATACodes() : Array<Airport>{
        return metricSearchRepository.getIataCodes().toTypedArray()
    }

    fun performValidation(origin: LiveData<Airport>, destination: LiveData<Airport>, currency: LiveData<Currency?>): MutableLiveData<String> {
        validationMessage.value = ""

        if (origin.value == null) {
            validationMessage.value = "Origin can not be blank"
        }

        if (destination.value == null) {
            validationMessage.value = "Destination can not be blank"
        }

        if (currency.value == null) {
            validationMessage.value = "Please select the currency"
        }
        return validationMessage
    }

    fun onDateSelected(departureDate: String, returnDate: String) {
        flightDateLiveData.value = departureDate
        returnDateLiveData.value = returnDate
    }
}