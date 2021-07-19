package com.java.flightscheduler.ui.flight.flightresults

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.ui.flight.flightresults.FlightResultsViewHolder

class FlightResultsAdapter(private val flightOffers: List<FlightOffer>)
    : RecyclerView.Adapter<FlightResultsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightResultsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_flight_search_item,parent,false)
        return FlightResultsViewHolder(view)
    }

    override fun onBindViewHolder(holderResults: FlightResultsViewHolder, position: Int) {
        holderResults.bind(flightOffers[position])
    }

    override fun getItemCount(): Int {
        return flightOffers.size
    }
}
