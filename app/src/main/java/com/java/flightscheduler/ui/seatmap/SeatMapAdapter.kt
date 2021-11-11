package com.java.flightscheduler.ui.seatmap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants.SEAT_MAP_AVAILABLE
import com.java.flightscheduler.data.constants.AppConstants.SEAT_MAP_BLOCKED
import com.java.flightscheduler.data.constants.AppConstants.SEAT_MAP_OCCUPIED
import com.java.flightscheduler.data.model.seatmap.base.Decks
import com.java.flightscheduler.data.model.seatmap.deck.seat.Seat
import com.java.flightscheduler.data.repository.SeatRepository
import com.java.flightscheduler.ui.base.BaseAdapter
import com.java.flightscheduler.ui.base.BaseViewHolder

class SeatMapAdapter(deck: Decks, private val onClick: (Seat) -> Unit) : BaseAdapter<Seat, ViewDataBinding>(R.layout.list_seat_map_item_available) {
    private val seatMapList = SeatRepository().getSeatLayout(deck)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : BaseViewHolder {
        when(viewType){
            -1 -> { binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_seat_map_item_aisle, parent, false) }
            0 -> { binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_seat_map_item_available, parent, false) }
            1 -> { binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_seat_map_item_occupied, parent, false) }
            2 -> { binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.list_seat_map_item_blocked,parent,false)}
        }
        return BaseViewHolder(binding?.root as ViewGroup)
    }

    override fun onBind(holder: BaseViewHolder, position: Int) {
        binding?.setVariable(BR.seat, seatMapList[position])
        binding?.executePendingBindings()
        binding?.root?.setOnClickListener {
            seatMapList[position].number.let {
                if (it != null) {
                    onClick(seatMapList[position])
                }
            }
        }
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return seatMapList.size
    }

    override fun getItemViewType(position: Int): Int {
        when (seatMapList[position].travelerPricing?.get(0)?.seatAvailabilityStatus) {
            SEAT_MAP_AVAILABLE -> return 0
            SEAT_MAP_OCCUPIED -> return 1
            SEAT_MAP_BLOCKED -> return 2
        }
        return -1
    }
}
