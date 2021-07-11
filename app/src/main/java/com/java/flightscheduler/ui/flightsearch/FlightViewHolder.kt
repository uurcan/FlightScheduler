package com.java.flightscheduler.ui.flightsearch

import androidx.recyclerview.widget.RecyclerView
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.databinding.ListFlightSearchItemBinding
import com.task.ui.base.listeners.RecyclerItemListener

class FlightViewHolder(private val itemBinding: ListFlightSearchItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(recipesItem: FlightOffer, recyclerItemListener: RecyclerItemListener) {
        itemBinding.txtFlightDetailOriginCity.text = recipesItem.lastTicketingDate
        itemBinding.txtFlightDetailDestinationCity.text = recipesItem.type
        itemBinding.txtFlightListPrice.text = recipesItem.lastTicketingDate
    }
}