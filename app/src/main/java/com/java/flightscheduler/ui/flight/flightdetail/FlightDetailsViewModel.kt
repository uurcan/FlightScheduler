package com.java.flightscheduler.ui.flight.flightdetail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.model.flight.pricing.FareDetailsBySegment
import com.java.flightscheduler.data.repository.FlightDetailsRepository

class FlightDetailsViewModel (context: Context,
                                         flightOffer: FlightOffer,
                                         segment: SearchSegment,
                                         fareDetailsBySegment: FareDetailsBySegment)
    : ViewModel(){
    private var flightDetailsRepository : FlightDetailsRepository = FlightDetailsRepository(context)
    private var flightSegmentLiveData : MutableLiveData<List<SearchSegment>> = MutableLiveData()
    private var fareDetailsLiveData : MutableLiveData<List<FareDetailsBySegment>> = MutableLiveData()

    val offer : MutableLiveData<FlightOffer> = MutableLiveData(flightDetailsRepository.getOffer(flightOffer))
    val origin: MutableLiveData<String> = MutableLiveData(flightDetailsRepository.getOrigin(segment))
    val destination : MutableLiveData<String> = MutableLiveData(flightDetailsRepository.getDestination(segment))
    val flightCode : MutableLiveData<String> = MutableLiveData(flightDetailsRepository.getFlightCode(segment))
    val duration : MutableLiveData<String> = MutableLiveData(flightDetailsRepository.getDuration(segment))
    val aircraft : MutableLiveData<String> = MutableLiveData(flightDetailsRepository.getAircraft(segment))
    val classCode : MutableLiveData<String> = MutableLiveData(flightDetailsRepository.getClassCode(fareDetailsBySegment))
    val fareBasis : MutableLiveData<String> = MutableLiveData(flightDetailsRepository.getFareBasis(fareDetailsBySegment))
    val cabinCode : MutableLiveData<String> = MutableLiveData(flightDetailsRepository.getCabinCode(fareDetailsBySegment))
    val formattedDate : MutableLiveData<String> = MutableLiveData(flightDetailsRepository.getFormattedFlightDate(segment))

    fun getSegments(flightOffer: FlightOffer): MutableLiveData<List<SearchSegment>> {
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