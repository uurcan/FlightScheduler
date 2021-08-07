package com.java.flightscheduler.ui.flight.flightdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.remote.repository.FlightDetailsRepository
import com.java.flightscheduler.databinding.ItemFlightDetailBinding

class FlightDetailsAdapter(flightOffer: FlightOffer)
    : RecyclerView.Adapter<FlightDetailsAdapter.FlightDetailsViewHolder>(){

    private val segments : List<SearchSegment>? = FlightDetailsRepository().getFlightDetails(flightOffer)
    private val legCount = FlightDetailsRepository().getLegCount(flightOffer)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlightDetailsAdapter.FlightDetailsViewHolder {
        return FlightDetailsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context) , R.layout.item_flight_detail , parent , false))
    }

    override fun onBindViewHolder(
        holder: FlightDetailsAdapter.FlightDetailsViewHolder,
        position: Int
    ) {
        segments?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return legCount!!
    }

    inner class FlightDetailsViewHolder(private var itemFlightDetailBinding: ItemFlightDetailBinding) :
        RecyclerView.ViewHolder(itemFlightDetailBinding.root){
        private lateinit var flightDetailsViewModel: FlightDetailsViewModel

        fun bind(segment: SearchSegment) {
            flightDetailsViewModel = FlightDetailsViewModel(segment)
            itemFlightDetailBinding.setVariable(BR.flightDetailViewModel , flightDetailsViewModel)
            itemFlightDetailBinding.executePendingBindings()
        }
    }
}