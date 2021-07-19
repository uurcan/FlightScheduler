package com.java.flightscheduler.ui.flight.flightresults

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import kotlinx.android.synthetic.main.list_flight_search_item.view.*
import java.lang.StringBuilder

class FlightResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(flightOffer: FlightOffer) {
        val segments : SearchSegment? = flightOffer.itineraries?.get(0)?.segments?.get(0)
        itemView.txt_flight_detail_origin_iata.text = segments?.departure?.iataCode
        itemView.txt_flight_detail_destination_iata.text = segments?.arrival?.iataCode
        itemView.txt_flight_detail_origin_local_date.text = segments?.departure?.at?.substring(11,16)
        itemView.txt_flight_detail_destination_local_date.text = segments?.arrival?.at?.substring(11,16)
        itemView.txt_flight_list_price.text = StringBuilder(flightOffer.price?.grandTotal.toString() + "€")
        itemView.flight_list_flight_number.text = StringBuilder(segments?.carrierCode + "-" + segments?.number)
        itemView.flight_list_flight_duration.text = segments?.duration
    }
}