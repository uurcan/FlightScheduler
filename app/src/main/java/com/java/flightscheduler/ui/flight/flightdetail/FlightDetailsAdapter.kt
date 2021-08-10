package com.java.flightscheduler.ui.flight.flightdetail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Aircraft
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.data.model.flight.pricing.FareDetailsBySegment
import com.java.flightscheduler.data.remote.repository.AircraftRepository
import com.java.flightscheduler.data.remote.repository.FlightDetailsRepository
import com.java.flightscheduler.databinding.ItemFlightDetailBinding
import kotlinx.android.synthetic.main.item_flight_detail.view.*

class FlightDetailsAdapter(flightOffer: FlightOffer,context : Context)
    : RecyclerView.Adapter<FlightDetailsAdapter.FlightDetailsViewHolder>(){

    private val segments : List<SearchSegment>? = FlightDetailsRepository().getSegmentDetails(flightOffer)
    private val fareDetails : List<FareDetailsBySegment>? = FlightDetailsRepository().getFareDetails(flightOffer)
    private val legCount = FlightDetailsRepository().getLegCount(flightOffer)
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
        val aircraftCode : String = segments?.get(position)?.aircraft?.code.toString()
        val aircraftName : String? = aircraftList.find { aircraft -> aircraftCode == aircraft.iata }?.name
        holder.itemView.txt_flight_detail_first_leg_aircraft_code.text = aircraftName

        holder.bind(segments!![position],fareDetails!![position])
    }

    override fun getItemCount(): Int {
        return legCount!!
    }

    inner class FlightDetailsViewHolder(private var itemFlightDetailBinding: ItemFlightDetailBinding) :
        RecyclerView.ViewHolder(itemFlightDetailBinding.root){
        private lateinit var flightDetailsViewModel: FlightDetailsViewModel

        fun bind(segment: SearchSegment,fareDetailsBySegment: FareDetailsBySegment) {
            flightDetailsViewModel = FlightDetailsViewModel(segment,fareDetailsBySegment)
            itemFlightDetailBinding.setVariable(BR.flightDetailViewModel,flightDetailsViewModel)
            itemFlightDetailBinding.executePendingBindings()
        }
    }
}