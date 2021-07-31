package com.java.flightscheduler.ui.flight.flightresults

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airlines
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.remote.repository.AirlineRepository

class FlightResultsAdapter(private val flightOffers: List<FlightOffer>,private val context : Context)
    : RecyclerView.Adapter<FlightResultsViewHolder>() {
    private val airlines : List<Airlines> = AirlineRepository(context).getAirlines()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightResultsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_flight_search_item,parent,false)
        return FlightResultsViewHolder(view)
    }

    override fun onBindViewHolder(holderResults: FlightResultsViewHolder, position: Int) {
        val carrierCode : String = flightOffers[position].itineraries?.get(0)?.segments?.get(0)?.carrierCode.toString()
        val iata = airlines.find { airline -> carrierCode == airline.ID }

        if (iata != null) {
            holderResults.bind(flightOffers[position],iata,context)
        }
    }

    override fun getItemCount(): Int {
        return flightOffers.size
    }
}
