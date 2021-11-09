package com.java.flightscheduler.ui.seatmap

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.databinding.FragmentSeatMapBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.ui.base.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatMapFragment : BaseFragment<SeatMapViewModel, FragmentSeatMapBinding>(R.layout.fragment_seat_map){
    override val viewModel: SeatMapViewModel? by viewModels()
    private lateinit var seatMapAdapter: SeatMapAdapter

    override fun onBind() {
        initializeResults()
    }

    private fun initializeResults() {
        viewModel?.getSeatMap()?.observeOnce { seatMap ->
            val layoutManager = GridLayoutManager(context, seatMap[0].decks?.get(0)?.deckConfiguration?.width ?: 11)
            binding?.rvSeatMap?.layoutManager = layoutManager

            seatMapAdapter = SeatMapAdapter(seatMap[0].decks?.get(0)?.seats!!)
            binding?.rvSeatMap?.adapter = seatMapAdapter
            binding?.setVariable(BR.seatMapHeader,seatMap[0])
        }
    }
}