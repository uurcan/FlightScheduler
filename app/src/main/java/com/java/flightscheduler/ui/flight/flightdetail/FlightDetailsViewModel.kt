package com.java.flightscheduler.ui.flight.flightdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.repository.FlightDetailsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FlightDetailsViewModel @Inject constructor(private val flightDetailsRepository: FlightDetailsRepository)
    : ViewModel(){
    private var flightSegmentLiveData : MutableLiveData<List<SearchSegment>> = MutableLiveData()

    fun getSegments(flightOffer: FlightOffer): MutableLiveData<List<SearchSegment>> {
        val searchSegments = flightDetailsRepository.getSegmentDetails(flightOffer)
        flightSegmentLiveData.postValue(searchSegments)
        return flightSegmentLiveData
    }
}