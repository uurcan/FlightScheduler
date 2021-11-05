package com.java.flightscheduler.ui.seatmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.seatmap.base.SeatMap
import com.java.flightscheduler.databinding.FragmentFlightSearchBinding
import com.java.flightscheduler.databinding.FragmentSeatMapBinding
import com.java.flightscheduler.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_seat_map.*

@AndroidEntryPoint
class SeatMapFragment : BaseFragment<SeatMapViewModel, FragmentSeatMapBinding>(R.layout.fragment_seat_map){
    override val viewModel: SeatMapViewModel? by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.getSeatMap()?.observe(viewLifecycleOwner,{
            metricsData ->
                if (metricsData != null) {
                    binding?.textSeatMap?.text = metricsData[0].decks?.get(0)?.seats?.get(12)?.cabin.toString()
                }
        })
    }

    override fun onBind() {

    }
}