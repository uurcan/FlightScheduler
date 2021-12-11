package com.java.flightscheduler.ui.flight.flightresults

import android.content.Context
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airline
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.repository.AirlineRepository
import com.java.flightscheduler.data.repository.FlightResultsRepository
import com.java.flightscheduler.data.repository.FlightSearchRepository
import com.java.flightscheduler.databinding.FlightListBinding
import com.java.flightscheduler.ui.base.BaseAdapter
import com.java.flightscheduler.ui.base.BaseViewHolder

class FlightResultsAdapter(private val onClick: (FlightOffer) -> Unit, context: Context)
    : BaseAdapter<FlightOffer, FlightListBinding>(R.layout.list_flight_search_item) {
    private var flightResultsRepository: FlightResultsRepository = FlightResultsRepository()
    private var airlineRepository: AirlineRepository = AirlineRepository(context)
    private var flightSearchRepository: FlightSearchRepository = FlightSearchRepository(context)
    private var airlines: List<Airline>
    private var flightRoutes: List<Airport>

    init {
        airlines = airlineRepository.getAirlines()
        flightRoutes = flightSearchRepository.getIataCodes()
    }

    override fun onBind(holder: BaseViewHolder, position: Int) {
        val segment: SearchSegment? = getItem(position).itineraries?.get(0)?.segments?.get(0)
        val carrier = airlineRepository.getMatchingAirline(airlines, segment?.carrierCode)
        val origin = flightSearchRepository.getMatchingFlightRoute(flightRoutes, segment?.departure?.iataCode)
        val destination = flightSearchRepository.getMatchingFlightRoute(flightRoutes, segment?.arrival?.iataCode)
        val flightInfo = flightSearchRepository.getFlightInfo(carrier, origin, destination)

        binding?.flightInfo = flightInfo
        binding?.flightListRepository = flightResultsRepository
        binding?.setVariable(BR.flightListItem, getItem(position))
        binding?.executePendingBindings()
        binding?.root?.setOnClickListener {
            onClick(getItem(position))
        }
    }
}
