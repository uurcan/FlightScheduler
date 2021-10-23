package com.java.flightscheduler.ui.flight.flightresults

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.repository.FlightResultsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlightResultsViewModel @Inject constructor(
    private val flightResultsRepository : FlightResultsRepository
): ViewModel() {

    fun getPrice(flightOffer: FlightOffer) : MutableLiveData<String> {
        return MutableLiveData(flightResultsRepository.getPriceResults(flightOffer))
    }
    fun getSegments(flightOffer: FlightOffer) : MutableLiveData<SearchSegment> {
        return MutableLiveData(flightResultsRepository.getSegments(flightOffer))
    }
    fun getFlightNumber(flightOffer: FlightOffer) : MutableLiveData<String> {
        return MutableLiveData(flightResultsRepository.getFlightNumber(flightOffer))
    }
    fun getDuration(flightOffer: FlightOffer) : MutableLiveData<String> {
        return MutableLiveData(flightResultsRepository.getDuration(flightOffer))
    }
}