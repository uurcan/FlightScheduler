package com.java.flightscheduler.ui.flight.flightdetail

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.remote.repository.FlightDetailsRepository

class FlightDetailsViewModel (private val segment: SearchSegment) : ViewModel(){

    private var flightDetailsRepository : FlightDetailsRepository = FlightDetailsRepository(segment)
    private var legCountLiveData : MutableLiveData<Int> = MutableLiveData()
    private var flightSegmentLiveData : MutableLiveData<List<SearchSegment>> = MutableLiveData()

    val origin: ObservableField<String> = ObservableField(flightDetailsRepository.getOrigin())
    val destination : ObservableField<String> = ObservableField(flightDetailsRepository.getDestination())
    val arrivalDate : ObservableField<String> = ObservableField(flightDetailsRepository.getArrivalDate())
    val departureDate : ObservableField<String> = ObservableField(flightDetailsRepository.getDepartureDate())
    val flightCode : ObservableField<String> = ObservableField(flightDetailsRepository.getFlightCode())
    val aircraft : String? = flightDetailsRepository.getAircraft()

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