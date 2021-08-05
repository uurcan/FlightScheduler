package com.java.flightscheduler.ui.flight.flightresults

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.java.flightscheduler.BR
import com.java.flightscheduler.data.model.flight.FlightInfo
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.databinding.FlightListBinding

class FlightResultsViewHolder(private var flightResultsBinding: FlightListBinding) :
    RecyclerView.ViewHolder(flightResultsBinding.root),FlightResultsAdapter.FlightResultsListener {

    private lateinit var flightOfferViewModel: FlightResultsViewModel

    fun bind(flightOffer: FlightOffer, flightInfo: FlightInfo ,context: Context) {
        flightOfferViewModel = FlightResultsViewModel(flightOffer, this)
        flightResultsBinding.setVariable(BR.flightListViewModel , flightOfferViewModel)
        flightResultsBinding.executePendingBindings()

        flightResultsBinding.flightListCarrierName.text = flightInfo.carrier.NAME
        Glide.with(context).load(flightInfo.carrier.LOGO).into(flightResultsBinding.flightListCarrierLogo)

        flightResultsBinding.txtFlightDetailOriginCity.text = flightInfo.origin
        flightResultsBinding.txtFlightDetailDestinationCity.text = flightInfo.destination
    }

    override fun onItemClick(view: View, item: FlightOffer) {

    }
}