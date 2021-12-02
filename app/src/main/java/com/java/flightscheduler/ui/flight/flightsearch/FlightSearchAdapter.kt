package com.java.flightscheduler.ui.flight.flightsearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airport
import kotlinx.android.synthetic.main.iata_list_item_view.view.*
import java.util.Locale

class FlightSearchAdapter(private val ctx: Context, private val iataCodes: Array<Airport>) :
    ArrayAdapter<Airport>(ctx, 0, iataCodes) {

    var filteredIataCodes: List<Airport> = listOf()

    override fun getCount(): Int = filteredIataCodes.size

    override fun getItem(position: Int): Airport = filteredIataCodes[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(ctx).inflate(R.layout.iata_list_item_view, parent, false)
        view.txt_flight_search_iata_data.text = filteredIataCodes[position].NAME
        view.txt_flight_search_iata_code.text = filteredIataCodes[position].IATA
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                filteredIataCodes = filterResults.values as List<Airport>
                notifyDataSetChanged()
            }
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase(Locale.ENGLISH)

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    iataCodes.asList()
                else
                    iataCodes.filter {
                        it.CITY.toString().toLowerCase(Locale.ENGLISH).contains(queryString) ||
                                it.NAME.toString().toLowerCase(Locale.ENGLISH).contains(queryString) ||
                                it.IATA.toString().toLowerCase(Locale.ENGLISH).contains(queryString)
                    }
                return filterResults
            }
        }
    }
}