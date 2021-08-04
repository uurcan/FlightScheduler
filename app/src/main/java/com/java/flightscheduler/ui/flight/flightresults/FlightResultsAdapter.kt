package com.java.flightscheduler.ui.flight.flightresults

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airlines
import com.java.flightscheduler.data.model.flight.FlightInfo
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.IATACodes
import com.java.flightscheduler.data.remote.repository.AirlineRepository
import com.java.flightscheduler.data.remote.repository.FlightRoutesRepository
import com.java.flightscheduler.ui.base.SelectedItemListener

class FlightResultsAdapter(flightOffers: List<FlightOffer>, private val context : Context)
    : RecyclerView.Adapter<FlightResultsViewHolder>() {
    private val airlines : List<Airlines> = AirlineRepository(context).getAirlines()
    private val locations : List<IATACodes> = FlightRoutesRepository(context).getIataCodes()
    private var filteredOffers : ArrayList<FlightOffer> = flightOffers.distinctBy { it.itineraries?.get(0)?.segments?.get(0)?.number } as ArrayList<FlightOffer>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightResultsViewHolder {
        return FlightResultsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context) , R.layout.list_flight_search_item , parent , false))
    }

    override fun onBindViewHolder(holderResults: FlightResultsViewHolder, position: Int) {
        val segment = filteredOffers[position].itineraries?.get(0)?.segments?.get(0)

        val carrier : Airlines? = airlines.find { data -> segment?.carrierCode == data.ID }
        val origin = locations.find { value -> segment?.departure?.iataCode == value.IATA_CODE }?.MUNICIPALITY.toString()
        val destination = locations.find { value -> segment?.arrival?.iataCode == value.IATA_CODE }?.MUNICIPALITY.toString()

        val flightInfo = carrier?.let { FlightInfo(it,origin,destination) }

        if (flightInfo != null) {
            holderResults.bind(filteredOffers[position], flightInfo, context)
        }
    }

    override fun getItemCount(): Int {
        return filteredOffers.size
    }
    interface FlightResultsListener : SelectedItemListener<FlightOffer>
}

