package com.java.flightscheduler.ui.flight.flightresults

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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

class FlightResultsAdapter(private val onClick: (FlightOffer) -> Unit)
    : ListAdapter<FlightOffer, FlightResultsAdapter.FlightResultsViewHolder>(FlightOffersDiffUtil()) {

    /*private val flightResultsRepository = FlightResultsRepository()
    private val airlineRepository : AirlineRepository = AirlineRepository(context)
    private val flightRoutesRepository : FlightRoutesRepository = FlightRoutesRepository(context)
    private var filteredOffers = flightRoutesRepository.getFilteredFlightResults(flightOffers)
    private var airlines : List<Airline> = airlineRepository.getAirlines()
    private var flightRoutes : List<Airport> = flightRoutesRepository.getIataCodes()
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightResultsViewHolder {
        return FlightResultsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context) , R.layout.list_flight_search_item , parent , false))
    }

    override fun onBindViewHolder(holderResults: FlightResultsViewHolder, position: Int) {
        /*val segment : SearchSegment? = filteredOffers[position].itineraries?.get(0)?.segments?.get(0)
        val carrier = airlineRepository.getMatchingAirline(airlines, segment?.carrierCode)
        val origin = flightRoutesRepository.getMatchingFlightRoute(flightRoutes, segment?.departure?.iataCode)
        val destination = flightRoutesRepository.getMatchingFlightRoute(flightRoutes, segment?.arrival?.iataCode)
        val flightInfo = flightRoutesRepository.getFlightInfo(carrier,origin,destination)
        */
        holderResults.bind(getItem(position))
    }

    interface FlightResultsListener : SelectedItemListener<FlightOffer>

    inner class FlightResultsViewHolder(private var flightResultsBinding: FlightListBinding) :
        RecyclerView.ViewHolder(flightResultsBinding.root) {

        fun bind(flightOffer: FlightOffer) {
            /*flightResultsBinding.flightInfo = flightInfo
            flightResultsBinding.flightListRepository = flightResultsRepository
            flightResultsBinding.setVariable(BR.flightListItem, flightOffer)
            flightResultsBinding.executePendingBindings()
            */
            flightResultsBinding.txtFlightDetailDestinationCity.text = flightOffer.id
            flightResultsBinding.root.setOnClickListener {
                onClick(flightOffer)
            }
        }
    }
    class FlightOffersDiffUtil : DiffUtil.ItemCallback<FlightOffer>(){
        override fun areItemsTheSame(oldItem: FlightOffer, newItem: FlightOffer): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: FlightOffer, newItem: FlightOffer): Boolean {
            TODO("Not yet implemented")
        }
    }
}

