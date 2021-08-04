package com.java.flightscheduler.ui.flight.flightresults

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.java.flightscheduler.BR
import com.java.flightscheduler.data.model.flight.Airlines
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.remote.repository.FlightRoutesRepository
import com.java.flightscheduler.databinding.FlightListBinding

class FlightResultsViewHolder(private var flightResultsBinding: FlightListBinding) :
    RecyclerView.ViewHolder(flightResultsBinding.root),FlightResultsAdapter.FlightResultsListener {

    private lateinit var flightOfferViewModel: FlightResultsViewModel
    private val flightRoutesRepository : FlightRoutesRepository = FlightRoutesRepository(context)

    fun bind(flightOffer: FlightOffer, airlines: Airlines,context: Context) {
        flightOfferViewModel = FlightResultsViewModel(flightOffer, this)
        flightResultsBinding.setVariable(BR.flightListViewModel , flightOfferViewModel)
        flightResultsBinding.executePendingBindings()

        flightResultsBinding.flightListCarrierName.text = airlines.NAME
        Glide.with(context).load(airlines.LOGO).into(flightResultsBinding.flightListCarrierLogo)

        flightResultsBinding.txtFlightDetailOriginCity.text = flightRoutesRepository.getNameFromData(flightOfferViewModel.origin)
        flightResultsBinding.txtFlightDetailDestinationCity.text = flightRoutesRepository.getNameFromData(flightOfferViewModel.destination)
    }

    override fun onItemClick(view: View, item: FlightOffer) {
g
    }
}