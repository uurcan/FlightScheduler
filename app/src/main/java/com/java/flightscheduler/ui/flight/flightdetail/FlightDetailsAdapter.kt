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
import com.java.flightscheduler.data.repository.*
import com.java.flightscheduler.databinding.ItemFlightDetailBinding
import com.java.flightscheduler.ui.base.BaseAdapter
import com.java.flightscheduler.ui.base.BaseViewHolder

class FlightDetailsAdapter(flightOffer: FlightOffer, private val context: Context, private val onClick: (SearchSegment?) -> Unit)
    : BaseAdapter<Itinerary, ItemFlightDetailBinding>(R.layout.item_flight_detail) {

    private val flightDetailsRepository = FlightDetailsRepository(context)
    private val flightRoutesRepository = FlightSearchRepository(context)
    private val predictionRepository = PredictionSearchRepository(context)
    private val airlineList = predictionRepository.getAirlines()
    private var airportList: List<Airport> = flightRoutesRepository.getIataCodes()
    private var connectionVariables: List<String> = flightDetailsRepository.getConnectionInfo(flightOffer)
    private var segments: List<SearchSegment>? = flightDetailsRepository.getSegmentDetails(flightOffer)
    private var fareDetails: List<FareDetailsBySegment>? = flightDetailsRepository.getFareDetails(flightOffer)
    private var aircraftList: List<Aircraft> = flightRoutesRepository.getAircraft()

    override fun onBind(holder: BaseViewHolder, position: Int) {
        binding?.fareDetails = fareDetails?.get(position)
        binding?.repository = flightDetailsRepository
        binding?.setVariable(BR.flightDetailSegment, segments?.get(position))
        binding?.executePendingBindings()
        binding?.root?.setOnClickListener {
            onClick(segments?.get(position))
        }
        bindCustomized(position)
    }

    override fun getItemCount(): Int {
        return segments?.size ?: 0
    }

    private fun bindCustomized(position: Int) {
        val aircraftCode: String = segments?.get(position)?.aircraft?.code.toString()
        val airlineProvider = predictionRepository.getMatchingAirline(airlineList, segments?.get(position)?.carrierCode)
        val departureAirportName: String? = flightRoutesRepository.getMatchingAirport(airportList, segments?.get(position)?.departure?.iataCode.toString())
        val arrivalAirportName: String? = flightRoutesRepository.getMatchingAirport(airportList, segments?.get(position)?.arrival?.iataCode.toString())
        val aircraftName: String? = flightRoutesRepository.getMatchingAircraft(aircraftList, aircraftCode)
        val conStatusText: TextView? = binding?.txtFlightDetailDetailsConnectionTime
        val destination = "$departureAirportName - $arrivalAirportName"

        binding?.textFlightDetailCarrier?.text = airlineProvider?.NAME
        binding?.txtFlightDetailAircraftCode?.text = aircraftName ?: context.getString(R.string.no_aircraft_found)
        binding?.txtFlightDetailDetailsConnectionTime?.text = connectionVariables[position]
        binding?.txtFlightDetailCityInfo?.text = destination
        when (connectionVariables[position]) {
            context.getString(R.string.text_end_flight) -> conStatusText?.setTextColor(ContextCompat.getColor(context, R.color.green_google))
            context.getString(R.string.text_return_segments) -> conStatusText?.setTextColor(ContextCompat.getColor(context, R.color.yellow_900))
            else -> {
                conStatusText?.setTextColor(ContextCompat.getColor(context, R.color.grey_500))
            }
        }
    }
}