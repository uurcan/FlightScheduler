package com.java.flightscheduler.ui.flight.flightresults

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airlines
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.remote.repository.AirlineRepository
import com.java.flightscheduler.data.remote.repository.FlightRoutesRepository
import com.java.flightscheduler.ui.base.SelectedItemListener

class FlightResultsAdapter(flightOffers: List<FlightOffer>, private val context : Context)
    : RecyclerView.Adapter<FlightResultsViewHolder>() {
    private val airlines : List<Airlines> = AirlineRepository(context).getAirlines()
    private val flightRoutesRepository : FlightRoutesRepository = FlightRoutesRepository(context)

    private var filteredOffers : ArrayList<FlightOffer> = flightOffers.distinctBy { it.itineraries?.get(0)?.segments?.get(0)?.number } as ArrayList<FlightOffer>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightResultsViewHolder {
        return FlightResultsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context) , R.layout.list_flight_search_item , parent , false))
    }

    override fun onBindViewHolder(holderResults: FlightResultsViewHolder, position: Int) {
        val segment = filteredOffers[position].itineraries?.get(0)?.segments?.get(0)
        val carrierCode : String = segment?.carrierCode.toString()
        val iata = airlines.find { airline -> carrierCode == airline.ID }


        if (iata != null) {
            holderResults.bind(filteredOffers[position], iata, context)
        }
    }

    override fun getItemCount(): Int {
        return filteredOffers.size
    }
    interface FlightResultsListener : SelectedItemListener<FlightOffer>
}

