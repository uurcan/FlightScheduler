package com.java.flightscheduler.ui.flight.flightdetail

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.model.flight.pricing.FareDetailsBySegment
import com.java.flightscheduler.data.remote.repository.FlightDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlightDetailsViewModel @Inject constructor(segment: SearchSegment,fareDetailsBySegment: FareDetailsBySegment) : ViewModel(){

    private var flightDetailsRepository : FlightDetailsRepository = FlightDetailsRepository()
    private var flightSegmentLiveData : MutableLiveData<List<SearchSegment>> = MutableLiveData()
    private var fareDetailsLiveData : MutableLiveData<List<FareDetailsBySegment>> = MutableLiveData()

    val origin: ObservableField<String> = ObservableField(flightDetailsRepository.getOrigin(segment))
    val destination : ObservableField<String> = ObservableField(flightDetailsRepository.getDestination(segment))
    val arrivalDate : ObservableField<String> = ObservableField(flightDetailsRepository.getArrivalDate(segment))
    val departureDate : ObservableField<String> = ObservableField(flightDetailsRepository.getDepartureDate(segment))
    val flightCode : ObservableField<String> = ObservableField(flightDetailsRepository.getFlightCode(segment))
    val duration : ObservableField<String> = ObservableField(flightDetailsRepository.getDuration(segment))
    val aircraft : ObservableField<String> = ObservableField(flightDetailsRepository.getAircraft(segment))
    val classCode : ObservableField<String> = ObservableField(flightDetailsRepository.getClassCode(fareDetailsBySegment))
    val fareBasis : ObservableField<String> = ObservableField(flightDetailsRepository.getFareBasis(fareDetailsBySegment))
    val cabinCode : ObservableField<String> = ObservableField(flightDetailsRepository.getCabinCode(fareDetailsBySegment))

    fun getSegments(flightOffer: FlightOffer) : MutableLiveData<List<SearchSegment>> {
        val searchSegments = flightDetailsRepository.getSegmentDetails(flightOffer)
        flightSegmentLiveData.postValue(searchSegments)
        return flightSegmentLiveData
    }

    fun getFareData(flightOffer: FlightOffer) : MutableLiveData<List<FareDetailsBySegment>> {
        val fareDetails = flightDetailsRepository.getFareDetails(flightOffer)
        fareDetailsLiveData.postValue(fareDetails)
        return fareDetailsLiveData
    }
}