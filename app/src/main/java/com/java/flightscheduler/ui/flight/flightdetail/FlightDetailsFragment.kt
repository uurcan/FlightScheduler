package com.java.flightscheduler.ui.flight.flightdetail

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.databinding.FragmentFlightDetailBinding
import com.java.flightscheduler.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightDetailsFragment : BaseFragment<FlightDetailsViewModel,FragmentFlightDetailBinding>
    (R.layout.fragment_flight_detail){
    private val args by navArgs<FlightDetailsFragmentArgs>()
    private val flightDetailsAdapter : FlightDetailsAdapter by lazy {
        FlightDetailsAdapter(args.offer, requireContext())
    }

    override fun init() {

    }

    override fun setViewModelFactory(): ViewModelProvider.Factory? {
        return defaultViewModelProviderFactory
    }

    override fun setViewModelClass(): Class<FlightDetailsViewModel> = FlightDetailsViewModel::class.java

    override fun onBind() {
        val flightOffer : FlightOffer = args.offer
        initializeFlightHeader(flightOffer)
        initializeFlightResults(flightOffer)
    }

    private fun initializeFlightHeader(flightOffer: FlightOffer) {
      binding?.setVariable(BR.flightOfferHeader,flightOffer)
    }

    private fun initializeFlightResults(flightOffer: FlightOffer) {
        val layoutManager = LinearLayoutManager(context)
        binding?.rvFlightDetail?.layoutManager = layoutManager
        binding?.rvFlightDetail?.setHasFixedSize(true)
        viewModel?.getSegments(flightOffer)?.observe(viewLifecycleOwner,{
            binding?.rvFlightDetail?.adapter = flightDetailsAdapter
            flightDetailsAdapter.submitList(flightOffer.itineraries)
        })
    }
}