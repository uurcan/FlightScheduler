package com.java.flightscheduler.ui.flight.flightresults

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airline
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.flight.FlightInfo
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.repository.AirlineRepository
import com.java.flightscheduler.data.repository.FlightResultsRepository
import com.java.flightscheduler.data.repository.FlightRoutesRepository
import com.java.flightscheduler.databinding.FlightListBinding
import com.java.flightscheduler.ui.base.SelectedItemListener

class FlightResultsAdapter(flightOffers: List<FlightOffer>,
                           private val context : Context,
                           private val listener: FlightResultsListener)
    : RecyclerView.Adapter<FlightResultsAdapter.FlightResultsViewHolder>() {

    private val airlineRepository : AirlineRepository = AirlineRepository(context)
    private val flightRoutesRepository : FlightRoutesRepository = FlightRoutesRepository(context)
    private var filteredOffers = flightRoutesRepository.getFilteredFlightResults(flightOffers)
    private var airlines : List<Airline> = airlineRepository.getAirlines()
    private var flightRoutes : List<Airport> = flightRoutesRepository.getIataCodes()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightResultsViewHolder {
        return FlightResultsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context) , R.layout.list_flight_search_item , parent , false))
    }

    override fun onBindViewHolder(holderResults: FlightResultsViewHolder, position: Int) {
        val segment : SearchSegment? = filteredOffers[position].itineraries?.get(0)?.segments?.get(0)
        val carrier = airlineRepository.getMatchingAirline(airlines, segment?.carrierCode)
        val origin = flightRoutesRepository.getMatchingFlightRoute(flightRoutes, segment?.departure?.iataCode)
        val destination = flightRoutesRepository.getMatchingFlightRoute(flightRoutes, segment?.arrival?.iataCode)
        val flightInfo = flightRoutesRepository.getFlightInfo(carrier,origin,destination)

        holderResults.bind(filteredOffers[position], flightInfo)
    }

    override fun getItemCount(): Int {
        return filteredOffers.size
    }

    interface FlightResultsListener : SelectedItemListener<FlightOffer>

    inner class FlightResultsViewHolder(private var flightResultsBinding: FlightListBinding) :
        RecyclerView.ViewHolder(flightResultsBinding.root) {

        private val flightResultsRepository = FlightResultsRepository()
        private val flightResultsViewModel = FlightResultsViewModel(flightResultsRepository)

        fun bind(flightOffer: FlightOffer, flightInfo: FlightInfo) {
            flightResultsBinding.setVariable(BR.flightOffer, flightOffer)
            flightResultsBinding.setVariable(BR.flightListViewModel , flightResultsViewModel)
            flightResultsBinding.executePendingBindings()

            flightResultsBinding.flightListCarrierName.text = flightInfo.carrier?.NAME
            Glide.with(context).load(flightInfo.carrier?.LOGO).into(flightResultsBinding.flightListCarrierLogo)

            flightResultsBinding.txtFlightDetailOriginCity.text = flightInfo.origin
            flightResultsBinding.txtFlightDetailDestinationCity.text = flightInfo.destination

            flightResultsBinding.root.setOnClickListener {
                listener.onItemClick(it, flightOffer)
            }
        }
    }
}

