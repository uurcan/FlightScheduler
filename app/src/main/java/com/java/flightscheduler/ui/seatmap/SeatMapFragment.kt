package com.java.flightscheduler.ui.seatmap

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.java.flightscheduler.R
import com.java.flightscheduler.databinding.FragmentSeatMapBinding
import com.java.flightscheduler.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatMapFragment : BaseFragment<SeatMapViewModel, FragmentSeatMapBinding>(R.layout.fragment_seat_map){
    override val viewModel: SeatMapViewModel? by viewModels()
    private val args : SeatMapFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.getSeatMap(args.flightSearch)?.observe(viewLifecycleOwner, {
            if (it != null) {
                binding?.textSeatMap?.text = it[0].decks?.get(0)?.seats?.get(12)?.cabin.toString()
            }
        })
    }

    override fun onBind() {

    }
}