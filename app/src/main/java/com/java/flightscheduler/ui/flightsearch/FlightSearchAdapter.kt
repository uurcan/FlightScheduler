package com.java.flightscheduler.ui.flightsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer

class FlightSearchAdapter(private val flightOffers: List<FlightOffer>)
    : RecyclerView.Adapter<FlightViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_flight_search_item,parent,false)
        return FlightViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        holder.bind(flightOffers[position])
    }

    override fun getItemCount(): Int {
        return flightOffers.size
    }
}
