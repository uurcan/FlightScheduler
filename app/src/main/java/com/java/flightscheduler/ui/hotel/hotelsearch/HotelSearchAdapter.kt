package com.java.flightscheduler.ui.hotel.hotelsearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.hotel.City
import kotlinx.android.synthetic.main.hotel_list_item_view.view.*
import java.util.Locale

class HotelSearchAdapter(private val ctx: Context, private val cities: Array<City>) :
    ArrayAdapter<City>(ctx, 0, cities) {

    var filteredCities: List<City> = listOf()

    override fun getCount(): Int = filteredCities.size

    override fun getItem(position: Int): City = filteredCities[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(ctx).inflate(R.layout.hotel_list_item_view, parent, false)
        view.txt_hotel_search_code.text = filteredCities[position].code
        view.txt_hotel_search_data.text = filteredCities[position].name
        return view
    }

    @Suppress("UNCHECKED_CAST")
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                filteredCities = filterResults.values as List<City>
                notifyDataSetChanged()
            }
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase(Locale.ENGLISH)

                val filterResults = FilterResults()
                filterResults.values = if (queryString == null || queryString.isEmpty())
                    cities.asList()
                else
                    cities.filter {
                        it.name?.toLowerCase(Locale.ENGLISH)?.contains(queryString) == true ||
                                it.country?.toLowerCase(Locale.ENGLISH)?.contains(queryString) == true ||
                                it.code?.toLowerCase(Locale.ENGLISH)?.contains(queryString) == true
                    }
                return filterResults
            }
        }
    }
}