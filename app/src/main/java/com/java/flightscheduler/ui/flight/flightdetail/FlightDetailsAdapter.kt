package com.java.flightscheduler.ui.flight.flightdetail

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Aircraft
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.Itinerary
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.model.flight.pricing.FareDetailsBySegment
import com.java.flightscheduler.data.model.seatmap.deck.seat.Seat
import com.java.flightscheduler.data.repository.AirlineRepository
import com.java.flightscheduler.data.repository.FlightDetailsRepository
import com.java.flightscheduler.data.repository.FlightRoutesRepository
import com.java.flightscheduler.databinding.ItemFlightDetailBinding
import com.java.flightscheduler.ui.base.BaseAdapter
import com.java.flightscheduler.ui.base.BaseViewHolder

class FlightDetailsAdapter(flightOffer: FlightOffer, private val context: Context,private val onClick: (Int) -> Unit)
    : BaseAdapter<Itinerary, ItemFlightDetailBinding>(R.layout.item_flight_detail){

    private val flightDetailsRepository = FlightDetailsRepository(context)
    private val flightRoutesRepository = FlightRoutesRepository(context)
    private val airlineRepository = AirlineRepository(context)
    private val airlineList = airlineRepository.getAirlines()
    private var airportList : List<Airport>
    private var connectionVariables : List<String>
    private var segments : List<SearchSegment>?
    private var fareDetails : List<FareDetailsBySegment>?
    private var legCount : Int?
    private var aircraftList : List<Aircraft>

    init {
        connectionVariables = flightDetailsRepository.getConnectionInfo(flightOffer)
        segments = flightDetailsRepository.getSegmentDetails(flightOffer)
        fareDetails = flightDetailsRepository.getFareDetails(flightOffer)
        legCount = flightDetailsRepository.getLegCount(flightOffer)
        airportList = flightRoutesRepository.getIataCodes()
        aircraftList = flightRoutesRepository.getAircraft()
    }

    override fun onBind(holder: BaseViewHolder, position: Int) {
        binding?.fareDetails = fareDetails?.get(position)
        binding?.repository = flightDetailsRepository
        binding?.setVariable(BR.flightDetailSegment, segments?.get(position))
        binding?.executePendingBindings()
        binding?.root?.setOnClickListener {
            onClick(position)
        }
        bindCustomized(position)
    }

    override fun getItemCount(): Int {
        return segments?.size ?: 0
    }

    private fun bindCustomized(position: Int) {
        val aircraftCode : String = segments?.get(position)?.aircraft?.code.toString()
        val airlineProvider = airlineRepository.getMatchingAirline(airlineList,segments?.get(position)?.carrierCode)
        val departureAirportName : String? = flightRoutesRepository.getMatchingAirport(airportList, segments?.get(position)?.departure?.iataCode.toString())
        val arrivalAirportName : String? = flightRoutesRepository.getMatchingAirport(airportList, segments?.get(position)?.arrival?.iataCode.toString())
        val aircraftName : String? = flightRoutesRepository.getMatchingAircraft(aircraftList,aircraftCode)
        val conStatusText : TextView? = binding?.txtFlightDetailDetailsConnectionTime
        val destination = "$departureAirportName - $arrivalAirportName"

        binding?.textFlightDetailCarrier?.text = airlineProvider?.NAME
        binding?.txtFlightDetailAircraftCode?.text = aircraftName ?: context.getString(R.string.no_aircraft_found)
        binding?.txtFlightDetailDetailsConnectionTime?.text = connectionVariables[position]
        binding?.txtFlightDetailCityInfo?.text = destination
        when (connectionVariables[position]) {
            context.getString(R.string.text_end_flight) -> conStatusText?.setTextColor(ContextCompat.getColor(context,R.color.green_google))
            context.getString(R.string.text_return_segments) -> conStatusText?.setTextColor(ContextCompat.getColor(context,R.color.yellow_900))
            else -> {
                conStatusText?.setTextColor(ContextCompat.getColor(context,R.color.grey_500))
            }
        }
    }
}