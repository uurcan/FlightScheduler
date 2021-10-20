package com.java.flightscheduler.ui.flight.flightdetail

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.databinding.FragmentFlightDetailBinding
import com.java.flightscheduler.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightDetailsFragment : BaseFragment<FlightDetailsViewModel,FragmentFlightDetailBinding>
    (R.layout.fragment_flight_detail){
    private val args by navArgs<FlightDetailsFragmentArgs>()
    private lateinit var flightDetailsAdapter : FlightDetailsAdapter

    override fun init() {

    }

    override fun setViewModelFactory(): ViewModelProvider.Factory? {
        return defaultViewModelProviderFactory
    }

    override fun setViewModelClass(): Class<FlightDetailsViewModel> = FlightDetailsViewModel::class.java

    override fun onBind() {
        val flightOffer : FlightOffer = args.offer
        initializeFlightResults(flightOffer)
    }

    private fun initializeFlightResults(flightOffer: FlightOffer) {
        val layoutManager = LinearLayoutManager(context)
        binding?.rvFlightDetail?.layoutManager = layoutManager
        binding?.rvFlightDetail?.setHasFixedSize(true)
        viewModel?.getSegments(flightOffer)?.observe(viewLifecycleOwner,{
            flightDetailsAdapter = context?.let { FlightDetailsAdapter(flightOffer, it) }!!
            binding?.rvFlightDetail?.adapter = flightDetailsAdapter
        })
    }
}