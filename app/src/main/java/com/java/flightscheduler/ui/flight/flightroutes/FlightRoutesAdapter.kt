package com.java.flightscheduler.ui.flight.flightroutes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.IATACodes
import kotlinx.android.synthetic.main.iata_list_item_view.view.*
import java.util.*

class FlightRoutesAdapter(private val ctx: Context, private val iataCodes: Array<IATACodes>) :
    ArrayAdapter<IATACodes>(ctx,0,iataCodes) {

    var filteredIataCodes: List<IATACodes> = listOf()

    override fun getCount(): Int = filteredIataCodes.size

    override fun getItem(position: Int): IATACodes = filteredIataCodes[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(ctx).inflate(R.layout.iata_list_item_view, parent, false)
        view.txt_flight_search_iata_data.text = filteredIataCodes[position].NAME
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                filteredIataCodes = filterResults.values as List<IATACodes>
                notifyDataSetChanged()
            }
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase(Locale.ENGLISH)

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    iataCodes.asList()
                else
                    iataCodes.filter {
                        it.MUNICIPALITY.toString().toLowerCase(Locale.ENGLISH).contains(queryString) ||
                                it.NAME.toString().toLowerCase(Locale.ENGLISH).contains(queryString) ||
                                it.IATA_CODE.toString().toLowerCase(Locale.ENGLISH).contains(queryString)
                    }
                return filterResults
            }
        }
    }
}