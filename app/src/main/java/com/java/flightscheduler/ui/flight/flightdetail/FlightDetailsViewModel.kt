package com.java.flightscheduler.ui.flight.flightdetail

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.remote.repository.FlightDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlightDetailsViewModel @Inject constructor(segment: SearchSegment) : ViewModel(){

    private var flightDetailsRepository : FlightDetailsRepository = FlightDetailsRepository()
    private var legCountLiveData : MutableLiveData<Int> = MutableLiveData()
    private var flightSegmentLiveData : MutableLiveData<List<SearchSegment>> = MutableLiveData()

    val origin: ObservableField<String> = ObservableField(flightDetailsRepository.getOrigin(segment))
    val destination : ObservableField<String> = ObservableField(flightDetailsRepository.getDestination(segment))
    val arrivalDate : ObservableField<String> = ObservableField(flightDetailsRepository.getArrivalDate(segment))
    val departureDate : ObservableField<String> = ObservableField(flightDetailsRepository.getDepartureDate(segment))
    val flightCode : ObservableField<String> = ObservableField(flightDetailsRepository.getFlightCode(segment))
    val duration : ObservableField<String> = ObservableField(flightDetailsRepository.getDuration(segment))
    val aircraft : String? = flightDetailsRepository.getAircraft(segment)

    fun getSegments(flightOffer: FlightOffer) : MutableLiveData<List<SearchSegment>> {
        val searchSegments = flightDetailsRepository.getFlightDetails(flightOffer)
        flightSegmentLiveData.postValue(searchSegments)
        return flightSegmentLiveData
    }

    fun getLegCount(flightOffer: FlightOffer): MutableLiveData<Int> {
        val legCount = flightDetailsRepository.getLegCount(flightOffer)
        legCountLiveData.postValue(legCount)
        return legCountLiveData
    }
}