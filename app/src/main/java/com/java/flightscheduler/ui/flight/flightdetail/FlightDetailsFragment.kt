package com.java.flightscheduler.ui.flight.flightdetail

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.databinding.FragmentFlightDetailBinding


class FlightDetailsFragment : Fragment(){
    private val args by navArgs<FlightDetailsFragmentArgs>()
    private val flightDetailsViewModel : FlightDetailsViewModel by viewModels()
    private lateinit var binding : FragmentFlightDetailBinding
    private lateinit var flightDetailsAdapter : FlightDetailsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flight_detail,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val flightOffer : FlightOffer = args.offer
        initializeFlightResults(flightOffer)
    }

    private fun initializeFlightResults(flightOffer: FlightOffer) {
        val layoutManager = LinearLayoutManager(context)
        binding.rvFlightDetail.layoutManager = layoutManager
        binding.rvFlightDetail.setHasFixedSize(true)
        flightDetailsViewModel.getSegments(flightOffer).observe(viewLifecycleOwner,{
            flightDetailsAdapter = context?.let { FlightDetailsAdapter(flightOffer, it) }!!
            binding.rvFlightDetail.adapter = flightDetailsAdapter
        })
    }
}