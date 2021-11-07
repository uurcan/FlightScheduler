package com.java.flightscheduler.ui.seatmap

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.java.flightscheduler.R
import com.java.flightscheduler.databinding.FragmentSeatMapBinding
import com.java.flightscheduler.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatMapFragment : BaseFragment<SeatMapViewModel, FragmentSeatMapBinding>(R.layout.fragment_seat_map){
    override val viewModel: SeatMapViewModel? by viewModels()
    private lateinit var seatMapAdapter: SeatMapAdapter

    override fun onBind() {
        viewModel?.getSeatMap()?.observe(viewLifecycleOwner, {
            if (it != null) {
                val layoutManager = GridLayoutManager(context, it[0].decks?.get(0)?.deckConfiguration?.width ?: 5)
                seatMapAdapter = SeatMapAdapter(it[0].decks?.get(0)?.seats!!)
                seatMapAdapter.submitList(it[0].decks?.get(0)?.seats)
                binding?.rvSeatMap?.layoutManager = layoutManager
                binding?.rvSeatMap?.adapter = seatMapAdapter
            }
        })
    }
}