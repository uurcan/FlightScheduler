package com.java.flightscheduler.ui.hotel.hotelresults

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.hotel.base.HotelOffer
import com.java.flightscheduler.data.repository.HotelSearchRepository
import com.java.flightscheduler.databinding.HotelListBinding
import com.java.flightscheduler.ui.base.SelectedItemListener


class HotelResultsAdapter(private val hotelOffers: List<HotelOffer>,
                           private val context: Context,
                           private val listener: HotelResultsListener)

    : RecyclerView.Adapter<HotelResultsAdapter.HotelResultsViewHolder>() {

    private val hotelImages : List<String> = HotelSearchRepository(context).getHotelImages()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelResultsViewHolder {
        return HotelResultsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.list_hotel_search_item,
            parent,
            false))
    }

    override fun onBindViewHolder(holderResults: HotelResultsViewHolder, position: Int) {
        hotelOffers[position].hotel?.media?.get(0)?.uri = hotelImages[position]
        holderResults.bind(hotelOffers[position])
    }

    override fun getItemCount(): Int {
        return hotelOffers.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    interface HotelResultsListener : SelectedItemListener<HotelOffer>

    inner class HotelResultsViewHolder(private var hotelResultsBinding: HotelListBinding) :
        RecyclerView.ViewHolder(hotelResultsBinding.root) {

        private lateinit var hotelOfferViewModel: HotelResultsViewModel

        fun bind(hotelOffer: HotelOffer) {
            hotelOfferViewModel = HotelResultsViewModel(hotelOffer, null)
            hotelResultsBinding.setVariable(BR.hotelListViewModel ,hotelOfferViewModel)
            hotelResultsBinding.executePendingBindings()

            hotelResultsBinding.root.setOnClickListener {
                listener.onItemClick(it, hotelOffer)
            }
            Glide.with(context).load(hotelOffer.hotel?.media?.get(0)?.uri)
                .placeholder(R.drawable.hotel_placeholder)
                .error(R.drawable.hotel_placeholder)
                .centerCrop()
                .into(hotelResultsBinding.imgHotelImage)
        }
    }
}

