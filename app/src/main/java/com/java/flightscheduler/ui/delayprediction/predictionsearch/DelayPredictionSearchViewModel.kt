package com.java.flightscheduler.ui.delayprediction.predictionsearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.flight.Airline
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.prediction.PredictionSearch
import com.java.flightscheduler.data.repository.PredictionSearchRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DelayPredictionSearchViewModel @Inject constructor(private val predictionRepository: PredictionSearchRepository) : BaseViewModel() {
    private val validationMessage = MutableLiveData("")

    private val originLiveData = MutableLiveData<Airport>()
    val origin: LiveData<Airport> get() = originLiveData

    private val destinationLiveData = MutableLiveData<Airport>()
    val destination: LiveData<Airport> get() = destinationLiveData

    private val flightDateLiveData = MutableLiveData<String>(predictionRepository.getToday())
    val flightDate: LiveData<String> get() = flightDateLiveData

    private val departureTimeLiveData = MutableLiveData<String>()
    val departureTime: LiveData<String> get() = departureTimeLiveData

    private val arrivalTimeLiveData = MutableLiveData<String>()
    val arrivalTime: LiveData<String> get() = arrivalTimeLiveData

    private val carrierAirlineLiveData = MutableLiveData<Airline>()
    val carrier: LiveData<Airline> get() = carrierAirlineLiveData

    private val flightNumberLiveData = MutableLiveData<Int>()
    val flightNumber: LiveData<Int?> get() = flightNumberLiveData

    fun performValidation(predictionSearch: PredictionSearch): MutableLiveData<String> {
        validationMessage.value = ""

        if (predictionSearch.origin == null) {
            validationMessage.value = "Origin can not be blank"
        }
        if (predictionSearch.destination == null) {
            validationMessage.value = "Destination can not be blank"
        }
        if (predictionSearch.departureDate == null) {
            validationMessage.value = "Flight Date can not be blank"
        }
        if (predictionSearch.arrivalTime == null) {
            validationMessage.value = "Arrival Time can not be blank"
        }
        if (predictionSearch.departureTime == null) {
            validationMessage.value = "Departure Time can not be blank"
        }
        if (predictionSearch.carrierCode == null) {
            validationMessage.value = "Carrier Code can not be blank"
        }
        if (predictionSearch.flightNumber == null){
            validationMessage.value = "Flight Number can not be blank"
        }
        return validationMessage
    }

    fun onDateSelected(flightDate: String) {
        flightDateLiveData.value = flightDate
    }

    fun setOriginAirport(airport: Airport) {
        originLiveData.value = airport
    }

    fun setDestinationAirport(airport: Airport) {
        destinationLiveData.value = airport
    }

    fun setCarrier(airline: Airline) {
        carrierAirlineLiveData.value = airline
    }

    fun setFlightNumber(flightNumber : Int) {
        flightNumberLiveData.value = flightNumber
    }

    fun getAirlines(): Array<Airline>? {
        return predictionRepository.getAirlines().toTypedArray()
    }

    fun getIATACodes(): Array<Airport>? {
        return predictionRepository.getIataCodes().toTypedArray()
    }
    fun setDepartureTime(timePickerResult: String) {
        departureTimeLiveData.value = timePickerResult
    }
    fun setArrivalTime(timePickerResult: String) {
        arrivalTimeLiveData.value = timePickerResult
    }
}