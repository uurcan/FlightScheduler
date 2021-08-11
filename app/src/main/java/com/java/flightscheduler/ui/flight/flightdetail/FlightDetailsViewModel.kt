package com.java.flightscheduler.ui.flight.flightdetail

import android.content.Context
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
class FlightDetailsViewModel @Inject constructor(context: Context,segment: SearchSegment,fareDetailsBySegment: FareDetailsBySegment)
    : ViewModel(){

    private var flightDetailsRepository : FlightDetailsRepository = FlightDetailsRepository(context)
    private var flightSegmentLiveData : MutableLiveData<List<SearchSegment>> = MutableLiveData()
    private var fareDetailsLiveData : MutableLiveData<List<FareDetailsBySegment>> = MutableLiveData()

    val origin: ObservableField<String> = ObservableField(flightDetailsRepository.getOrigin(segment))
    val destination : ObservableField<String> = ObservableField(flightDetailsRepository.getDestination(segment))
    val flightCode : ObservableField<String> = ObservableField(flightDetailsRepository.getFlightCode(segment))
    val duration : ObservableField<String> = ObservableField(flightDetailsRepository.getDuration(segment))
    val aircraft : ObservableField<String> = ObservableField(flightDetailsRepository.getAircraft(segment))
    val classCode : ObservableField<String> = ObservableField(flightDetailsRepository.getClassCode(fareDetailsBySegment))
    val fareBasis : ObservableField<String> = ObservableField(flightDetailsRepository.getFareBasis(fareDetailsBySegment))
    val cabinCode : ObservableField<String> = ObservableField(flightDetailsRepository.getCabinCode(fareDetailsBySegment))
    val formattedDate : String = flightDetailsRepository.getFormattedFlightDate(segment)

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