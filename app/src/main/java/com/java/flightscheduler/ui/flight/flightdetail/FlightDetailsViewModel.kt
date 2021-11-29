package com.java.flightscheduler.ui.flight.flightdetail

import androidx.lifecycle.MutableLiveData
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.repository.FlightDetailsRepository
import com.java.flightscheduler.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlightDetailsViewModel @Inject constructor(private val flightDetailsRepository: FlightDetailsRepository)
    : BaseViewModel() {
    private var flightSegmentLiveData: MutableLiveData<List<SearchSegment>> = MutableLiveData()
    private var flightOfferTemplateLiveData: MutableLiveData<FlightOffer> = MutableLiveData()
    private var flightRequestLiveData: MutableLiveData<String> = MutableLiveData()

    fun getSeatMapRequestFromFlightOffer(flightOffer: FlightOffer?): MutableLiveData<String> {
        flightRequestLiveData.value = flightDetailsRepository.getSeatMapRequestFromFlightOffer(flightOffer)
        return flightRequestLiveData
    }

    fun getFlightOfferTemplate(segment: SearchSegment): MutableLiveData<FlightOffer> {
        flightOfferTemplateLiveData.value.apply {
            flightOfferTemplateLiveData.value = flightDetailsRepository.getOfferTemplate(segment)
        }
        return flightOfferTemplateLiveData
    }

    fun getSegments(flightOffer: FlightOffer): MutableLiveData<List<SearchSegment>> {
        val searchSegments = flightDetailsRepository.getSegmentDetails(flightOffer)
        flightSegmentLiveData.postValue(searchSegments)
        return flightSegmentLiveData
    }
}