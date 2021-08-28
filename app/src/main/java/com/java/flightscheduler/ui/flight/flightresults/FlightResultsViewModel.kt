package com.java.flightscheduler.ui.flight.flightresults

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.model.flight.itineraries.Itinerary
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.remote.repository.FlightRepository
import com.java.flightscheduler.data.remote.repository.FlightResultsRepository
import androidx.lifecycle.viewModelScope
import com.java.flightscheduler.data.model.base.BaseApiResult
import kotlinx.coroutines.launch

class FlightResultsViewModel constructor(
    private val flightRepository: FlightRepository?,
    flightOffer: FlightOffer,
    private val itemClickListener: FlightResultsAdapter.FlightResultsListener) : ViewModel() {

    constructor(
        flightOffer: FlightOffer,
        itemClickListener: FlightResultsAdapter.FlightResultsListener
    ) : this(
        flightOffer = flightOffer,
        itemClickListener = itemClickListener,
        flightRepository = null
    )

    private var flightResultsRepository : FlightResultsRepository = FlightResultsRepository(flightOffer)
    val price: ObservableField<String> = ObservableField(flightResultsRepository.getPriceResults())
    val itineraries : ObservableField<Itinerary> = ObservableField(flightResultsRepository.getItineraries())
    val segments : ObservableField<SearchSegment> = ObservableField(flightResultsRepository.getSegments())
    val flightNumber : ObservableField<String> = ObservableField(flightResultsRepository.getFlightNumber())
    val duration : ObservableField<String> = ObservableField(flightResultsRepository.getDuration())
    val carrierCode : String = flightResultsRepository.getCarrierCode().toString()


    var loadingLiveData : MutableLiveData<Boolean> = MutableLiveData()
    var errorLiveData : MutableLiveData<String>? = MutableLiveData()
    private var flightLiveData : MutableLiveData<List<FlightOffer>>? = MutableLiveData()

    fun getFlightData(flightSearch: FlightSearch) : MutableLiveData<List<FlightOffer>>?{
        loadingLiveData.value = true

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
                    //todo : not working as expected.
                    errorLiveData?.value = flightOffersSearches.errors[0].detail
                    loadingLiveData.value = false
                }
            }
        }
        return flightLiveData
    }

    fun onItemClick(view: View) {
        itemClickListener.onItemClick(view,flightResultsRepository.getResults())
    }
}