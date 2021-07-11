package com.java.flightscheduler.ui.flightsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.java.flightscheduler.databinding.FragmentFlightListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightSearchFragment : Fragment() {
    private lateinit var binding: FragmentFlightListBinding
    private lateinit var flightSearchViewModel: FlightSearchViewModel
    private lateinit var flightSearchAdapter : FlightSearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentFlightListBinding = FragmentFlightListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        binding.rvFlightList.layoutManager = layoutManager
        binding.rvFlightList.setHasFixedSize(true)
        flightSearchViewModel.getFlightData()

        flightSearchViewModel = ViewModelProvider(this).get(FlightSearchViewModel::class.java)
        flightSearchViewModel.getFlightData()?.observe(viewLifecycleOwner, {
            flightData ->
                flightSearchAdapter = FlightSearchAdapter(flightData)
                binding.rvFlightList.adapter = flightSearchAdapter
        })
    }
}
