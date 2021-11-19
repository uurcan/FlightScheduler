package com.java.flightscheduler.ui.seatmap.seatmapresults

import androidx.databinding.ViewDataBinding
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.seatmap.base.Decks
import com.java.flightscheduler.data.model.seatmap.deck.seat.Seat
import com.java.flightscheduler.data.repository.SeatRepository
import com.java.flightscheduler.ui.base.BaseAdapter
import com.java.flightscheduler.ui.base.BaseViewHolder

class SeatMapAdapter(deck: Decks, private val onClick: (Seat) -> Unit)
    : BaseAdapter<Seat, ViewDataBinding>(R.layout.list_seat_map_item) {

    private val seatMapList = SeatRepository().getSeatLayout(deck)

    override fun onBind(holder: BaseViewHolder, position: Int) {
        binding?.setVariable(BR.seat, seatMapList[position])
        binding?.executePendingBindings()
        binding?.root?.setOnClickListener {
            if (seatMapList[position].cabin != null) {
                onClick(seatMapList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return seatMapList.size
    }
}
