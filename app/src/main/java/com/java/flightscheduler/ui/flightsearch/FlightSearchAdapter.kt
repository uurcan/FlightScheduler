package com.java.flightscheduler.ui.flightsearch

import androidx.recyclerview.widget.DiffUtil
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.databinding.ItemFlightSearchBinding
import com.java.flightscheduler.ui.base.BaseListAdapter
import com.java.flightscheduler.utils.setSingleClick

class FlightSearchAdapter (
    val itemClickListener: (FlightOffer) -> Unit = {}
) : BaseListAdapter<FlightOffer, ItemFlightSearchBinding>(object  : DiffUtil.ItemCallback<FlightOffer>() {
    override fun areItemsTheSame(oldItem: FlightOffer, newItem: FlightOffer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FlightOffer, newItem: FlightOffer): Boolean {
        return oldItem == newItem
    }
}) {
    override fun getLayoutRes(viewType: Int): Int {
        return R.layout.item_flight_search
    }

    override fun bindFirstTime(binding: ItemFlightSearchBinding) {
        binding.apply {
            root.setSingleClick {
                flight?.apply {
                    itemClickListener(this)
                }
            }
        }
    }

    override fun bindView(binding: ItemFlightSearchBinding, item: FlightOffer, position: Int) {
        TODO("Not yet implemented")
    }
}