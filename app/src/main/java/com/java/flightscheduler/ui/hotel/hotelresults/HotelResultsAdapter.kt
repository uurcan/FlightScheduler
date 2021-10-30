package com.java.flightscheduler.ui.hotel.hotelresults

import android.content.Context
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.hotel.base.HotelOffer
import com.java.flightscheduler.data.repository.HotelSearchRepository
import com.java.flightscheduler.databinding.HotelListBinding
import com.java.flightscheduler.ui.base.BaseAdapter
import com.java.flightscheduler.ui.base.BaseViewHolder

class HotelResultsAdapter(private val onClick: (HotelOffer) -> Unit, context: Context)
    : BaseAdapter<HotelOffer,HotelListBinding>(R.layout.list_hotel_search_item) {

    private val hotelImages : List<String> = HotelSearchRepository(context).getHotelImages()

    override fun onBind(holder: BaseViewHolder, position: Int) {
        getItem(position).hotel?.media?.get(0)?.uri = hotelImages[position]

        binding?.setVariable(BR.hotelOfferList, getItem(position))
        binding?.executePendingBindings()
        binding?.buttonFlightOffersMain?.setOnClickListener {
            onClick(getItem(position))
        }
    }
}

