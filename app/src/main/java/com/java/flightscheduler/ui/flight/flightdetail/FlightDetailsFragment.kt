package com.java.flightscheduler.ui.flight.flightdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.java.flightscheduler.R
import com.java.flightscheduler.databinding.FragmentFlightDetailBinding

class FlightDetailsFragment : Fragment() {
    lateinit var  binding : FragmentFlightDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flight_detail,container,false)
        return binding.root
    }
}