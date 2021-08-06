package com.java.flightscheduler.ui.flight.flightresults

import android.view.View
import androidx.databinding.ObservableField
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.Itinerary
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.remote.repository.FlightResultsRepository

class FlightResultsViewModel(flightOffer: FlightOffer, private val itemClickListener: FlightResultsAdapter.FlightResultsListener) {
    private var flightResultsRepository : FlightResultsRepository = FlightResultsRepository(flightOffer)
    val price: ObservableField<String> = ObservableField(flightResultsRepository.getPriceResults())
    val itineraries : ObservableField<Itinerary> = ObservableField(flightResultsRepository.getItineraries())
    val segments : ObservableField<SearchSegment> = ObservableField(flightResultsRepository.getSegments())
    val flightNumber : ObservableField<String> = ObservableField(flightResultsRepository.getFlightNumber())
    val duration : ObservableField<String> = ObservableField(flightResultsRepository.getDuration())
    val carrierCode : String = flightResultsRepository.getCarrierCode().toString()

    fun onItemClick(view: View) {
        itemClickListener.onItemClick(view,flightResultsRepository.getResults())
    }
}