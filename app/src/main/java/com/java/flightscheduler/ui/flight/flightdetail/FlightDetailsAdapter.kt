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
import com.java.flightscheduler.data.remote.repository.AircraftRepository
import com.java.flightscheduler.data.remote.repository.FlightDetailsRepository
import com.java.flightscheduler.data.remote.repository.FlightRoutesRepository
import com.java.flightscheduler.databinding.ItemFlightDetailBinding

class FlightDetailsAdapter(flightOffer: FlightOffer,private val context : Context)
    : RecyclerView.Adapter<FlightDetailsAdapter.FlightDetailsViewHolder>(){

    private val airportList : List<Airport> = FlightRoutesRepository(context).getIataCodes()
    private val connectionVariables : List<String> = FlightDetailsRepository(context).getConnectionInfo(flightOffer)
    private val segments : List<SearchSegment>? = FlightDetailsRepository(context).getSegmentDetails(flightOffer)
    private val fareDetails : List<FareDetailsBySegment>? = FlightDetailsRepository(context).getFareDetails(flightOffer)
    private val legCount = FlightDetailsRepository(context).getLegCount(flightOffer)
    private val aircraftList : List<Aircraft> = AircraftRepository(context).getAircraft()

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
            segments!![position],
            fareDetails!![position],
            connectionVariables[position]
        )
    }

    override fun getItemCount(): Int {
        return legCount!!
    }

    inner class FlightDetailsViewHolder(private var itemFlightDetailBinding: ItemFlightDetailBinding) :
        RecyclerView.ViewHolder(itemFlightDetailBinding.root){
        private lateinit var flightDetailsViewModel: FlightDetailsViewModel

        fun bind(
            segment: SearchSegment,
            fareDetailsBySegment: FareDetailsBySegment,
            status: String
        ) {
            flightDetailsViewModel = FlightDetailsViewModel(context,segment,fareDetailsBySegment)
            itemFlightDetailBinding.setVariable(BR.flightDetailViewModel,flightDetailsViewModel)
            itemFlightDetailBinding.executePendingBindings()

            val aircraftCode : String = segment.aircraft?.code.toString()
            val departureAirportName : String? = airportList.find { port -> segment.departure?.iataCode.toString() == port.IATA }?.CITY
            val arrivalAirportName : String? = airportList.find { port -> segment.arrival?.iataCode.toString() == port.IATA }?.CITY
            val aircraftName : String? = aircraftList.find { aircraft -> aircraftCode == aircraft.iata }?.name
            val conStatusText : TextView = itemFlightDetailBinding.txtFlightDetailDetailsConnectionTime
            val destination = "$departureAirportName - $arrivalAirportName"

            itemFlightDetailBinding.txtFlightDetailAircraftCode.text = aircraftName
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