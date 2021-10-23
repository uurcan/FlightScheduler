package com.java.flightscheduler.ui.flight.flightdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Aircraft
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.model.flight.pricing.FareDetailsBySegment
import com.java.flightscheduler.data.repository.FlightDetailsRepository
import com.java.flightscheduler.data.repository.FlightRoutesRepository
import com.java.flightscheduler.databinding.ItemFlightDetailBinding

class FlightDetailsAdapter(flightOffer: FlightOffer, private val context : Context)
    : RecyclerView.Adapter<FlightDetailsAdapter.FlightDetailsViewHolder>(){

    private val flightDetailsRepository = FlightDetailsRepository(context)
    private val flightRoutesRepository = FlightRoutesRepository(context)
    private var airportList : List<Airport>
    private var connectionVariables : List<String>
    private var segments : List<SearchSegment>
    private var fareDetails : List<FareDetailsBySegment>?
    private var legCount : Int?
    private var aircraftList : List<Aircraft>

    init {
        connectionVariables = flightDetailsRepository.getConnectionInfo(flightOffer)
        segments = flightDetailsRepository.getSegmentDetails(flightOffer)!!
        fareDetails = flightDetailsRepository.getFareDetails(flightOffer)
        legCount = flightDetailsRepository.getLegCount(flightOffer)
        airportList = flightRoutesRepository.getIataCodes()
        aircraftList = flightRoutesRepository.getAircraft()
    }

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
        holder.bind(
            segments[position],
            fareDetails?.get(position),
            connectionVariables[position]
        )
    }

    override fun getItemCount(): Int {
        return legCount ?: 0
    }

    inner class FlightDetailsViewHolder(private var itemFlightDetailBinding: ItemFlightDetailBinding) :
        RecyclerView.ViewHolder(itemFlightDetailBinding.root){

        fun bind(
            segment: SearchSegment,
            fareDetailsBySegment: FareDetailsBySegment?,
            status: String
        ) {
            itemFlightDetailBinding.fareDetails = fareDetailsBySegment
            itemFlightDetailBinding.repository = flightDetailsRepository
            itemFlightDetailBinding.setVariable(BR.flightDetailSegment,segment)
            itemFlightDetailBinding.executePendingBindings()

            val aircraftCode : String = segment.aircraft?.code.toString()
            val departureAirportName : String? = flightRoutesRepository.getMatchingAirport(airportList,segment.departure?.iataCode.toString())
            val arrivalAirportName : String? = flightRoutesRepository.getMatchingAirport(airportList,segment.arrival?.iataCode.toString())
            val aircraftName : String? = flightRoutesRepository.getMatchingAircraft(aircraftList,aircraftCode)
            val conStatusText : TextView = itemFlightDetailBinding.txtFlightDetailDetailsConnectionTime
            val destination = "$departureAirportName - $arrivalAirportName"

            itemFlightDetailBinding.txtFlightDetailAircraftCode.text = aircraftName ?: context.getString(R.string.no_aircraft_found)
            itemFlightDetailBinding.txtFlightDetailDetailsConnectionTime.text = status
            itemFlightDetailBinding.txtFlightDetailCityInfo.text = destination
            when (status) {
                context.getString(R.string.text_end_flight) -> conStatusText.setTextColor(ContextCompat.getColor(context,R.color.green_google))
                context.getString(R.string.text_return_segments) -> conStatusText.setTextColor(ContextCompat.getColor(context,R.color.yellow_900))
                else -> {
                    conStatusText.setTextColor(ContextCompat.getColor(context,R.color.grey_500))
                }
            }
        }
    }
}