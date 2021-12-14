package com.java.flightscheduler.ui.delayprediction.predictionsearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airline
import kotlinx.android.synthetic.main.airline_list_item_view.view.*
import java.util.*

class DelayPredictionSearchAdapter(private val ctx: Context, private val iataCodes: Array<Airline>) :
    ArrayAdapter<Airline>(ctx, 0, iataCodes) {

    var filteredIataCodes: List<Airline> = listOf()

    override fun getCount(): Int = filteredIataCodes.size

    override fun getItem(position: Int): Airline = filteredIataCodes[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(ctx).inflate(R.layout.airline_list_item_view, parent, false)
        view.txt_prediction_date_name.text = filteredIataCodes[position].NAME
        view.txt_prediction_date_code.text = filteredIataCodes[position].ID
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                @Suppress("UNCHECKED_CAST")
                filteredIataCodes = filterResults.values as List<Airline>
                notifyDataSetChanged()
            }
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase(Locale.ENGLISH)

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    iataCodes.asList()
                else
                    iataCodes.filter {
                        it.ID.toString().toLowerCase(Locale.ENGLISH).contains(queryString) ||
                                it.NAME.toString().toLowerCase(Locale.ENGLISH).contains(queryString)
                    }
                return filterResults
            }
        }
    }
}