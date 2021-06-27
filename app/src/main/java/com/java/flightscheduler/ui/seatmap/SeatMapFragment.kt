package com.java.flightscheduler.ui.seatmap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.java.flightscheduler.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_seat_map.*

@AndroidEntryPoint
class SeatMapFragment : Fragment(){
    private lateinit var seatMapViewModel: SeatMapViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_seat_map,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seatMapViewModel = ViewModelProvider(this).get(SeatMapViewModel::class.java)
        seatMapViewModel.getMetricsData()?.observe(viewLifecycleOwner,{
            metricsData ->
                if (metricsData != null) {
                    text_seat_map.text = metricsData[0].decks?.get(0)?.seats?.get(1)?.cabin.toString()
                }
        })
    }
}