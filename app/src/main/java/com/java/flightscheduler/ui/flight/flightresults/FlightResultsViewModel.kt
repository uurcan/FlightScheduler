package com.java.flightscheduler.ui.flight.flightresults

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.java.flightscheduler.data.model.base.BaseApiResult
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.model.flight.itineraries.Itinerary
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.remote.repository.FlightRepository
import com.java.flightscheduler.data.remote.repository.FlightResultsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightResultsViewModel @Inject constructor(
    flightOffer: FlightOffer,
    private val flightRepository: FlightRepository?
): ViewModel(){

    private var flightResultsRepository : FlightResultsRepository = FlightResultsRepository(flightOffer)
    var loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    var errorLiveData : MutableLiveData<String>? = MutableLiveData()
    private var flightLiveData : MutableLiveData<List<FlightOffer>>? = MutableLiveData()

    init {
        loadingLiveData.value = true
    }

    val price: MutableLiveData<String> = MutableLiveData(flightResultsRepository.getPriceResults())
    val itineraries : MutableLiveData<Itinerary> = MutableLiveData(flightResultsRepository.getItineraries())
    val segments : MutableLiveData<SearchSegment> = MutableLiveData(flightResultsRepository.getSegments())
    val flightNumber : MutableLiveData<String> = MutableLiveData(flightResultsRepository.getFlightNumber())
    val duration : MutableLiveData<String> = MutableLiveData(flightResultsRepository.getDuration())
    val carrierCode : String = flightResultsRepository.getCarrierCode().toString()

    fun getFlightData(flightSearch: FlightSearch) : MutableLiveData<List<FlightOffer>>?{
        viewModelScope.launch {
            val flightOffersSearches = flightRepository?.get(
                originLocationCode = flightSearch.originLocationCode,
                destinationLocationCode = flightSearch.destinationLocationCode,
                departureDate = flightSearch.departureDate,
                returnDate = flightSearch.returnDate,
                adults = flightSearch.adults,
                children = flightSearch.children
            )

            when (flightOffersSearches){
                is BaseApiResult.Success -> {
                    flightLiveData.apply {
                        flightLiveData?.postValue(flightOffersSearches.data)
                        loadingLiveData.value = false
                    }
                }
                is BaseApiResult.Error -> {
                    errorLiveData?.value = flightResultsRepository.getQueryErrors(flightOffersSearches.errors)
                    loadingLiveData.value = false
                }
            }
        }
        return flightLiveData
    }
}