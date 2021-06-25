package com.java.flightscheduler.ui.flightsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.java.flightscheduler.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_flight_offers.*

@AndroidEntryPoint
class FlightSearchFragment : Fragment() {
    private lateinit var flightSearchViewModel: FlightSearchViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flight_offers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flightSearchViewModel = ViewModelProvider(this).get(FlightSearchViewModel::class.java)
        flightSearchViewModel.getFlightData()?.observe(viewLifecycleOwner, Observer {
            flightData ->
            if (flightData != null)
                text_flight_offers.text = flightData[0].id.toString()
        })
    }
}
