package com.java.flightscheduler.ui.seatmap

import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.seatmap.deck.seat.Seat
import com.java.flightscheduler.data.repository.SeatRepository
import com.java.flightscheduler.databinding.ListSeatMapItemBinding
import com.java.flightscheduler.ui.base.BaseAdapter
import com.java.flightscheduler.ui.base.BaseViewHolder

class SeatMapAdapter(seatList: List<Seat>) : BaseAdapter<Seat,ListSeatMapItemBinding>(R.layout.list_seat_map_item) {
    private val seatMapList = SeatRepository().getSeatLayout(seatList)

    override fun onBind(holder: BaseViewHolder, position: Int) {
        binding?.setVariable(BR.seat, seatMapList[position])
        binding?.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return seatMapList.size
    }
}
