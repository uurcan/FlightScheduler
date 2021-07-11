package com.java.flightscheduler.ui.flightsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.databinding.ListFlightSearchItemBinding
import com.task.ui.base.listeners.RecyclerItemListener

class FlightSearchAdapter(private val flightOffers: List<FlightOffer>)
    : RecyclerView.Adapter<FlightViewHolder>() {

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(recipe: FlightOffer) {
            //todo details
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightViewHolder {
        val itemBinding = ListFlightSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FlightViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: FlightViewHolder, position: Int) {
        holder.bind(flightOffers[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return flightOffers.size
    }
}
