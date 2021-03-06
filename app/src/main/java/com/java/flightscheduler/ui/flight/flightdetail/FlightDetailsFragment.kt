package com.java.flightscheduler.ui.flight.flightdetail

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.databinding.FragmentFlightDetailBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.utils.MessageHelper
import com.java.flightscheduler.utils.extension.showDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightDetailsFragment : BaseFragment<FlightDetailsViewModel, FragmentFlightDetailBinding>
    (R.layout.fragment_flight_detail) {
    private val args by navArgs<FlightDetailsFragmentArgs>()
    override val viewModel: FlightDetailsViewModel by viewModels()

    private val flightDetailsAdapter: FlightDetailsAdapter by lazy {
        FlightDetailsAdapter(args.offer, requireContext()) {
            onFlightSegmentSelected(it!!)
        }
    }

    private fun onFlightSegmentSelected(segment: SearchSegment) {
        val offer = viewModel.getFlightOfferTemplate(segment).value
        viewModel.getSeatMapRequestFromFlightOffer(offer).observe(viewLifecycleOwner, {
            directToSeatMap(it)
        })
    }

    override fun onBind() {
        val flightOffer: FlightOffer = args.offer
        initializeFlightHeader(flightOffer)
        initializeFlightResults(flightOffer)
        setHasOptionsMenu(true)
    }

    private fun initializeFlightHeader(flightOffer: FlightOffer) {
        binding?.setVariable(BR.flightOfferHeader, flightOffer)
    }

    private fun initializeFlightResults(flightOffer: FlightOffer) {
        binding?.rvFlightDetail?.setHasFixedSize(true)
        viewModel.getSegments(flightOffer).observe(viewLifecycleOwner, {
            binding?.rvFlightDetail?.adapter = flightDetailsAdapter
            flightDetailsAdapter.submitList(flightOffer.itineraries)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_seat_map -> {
                showDialog(message = getString(R.string.text_seat_map_button), textPositive = getString(R.string.OK))
            }
            R.id.action_save_offer -> MessageHelper.displaySuccessMessage(view, "On Save Offer Click")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun directToSeatMap(request: String) {
        val action = FlightDetailsFragmentDirections
            .actionNavFlightResultsToSeatMapFragment(null, request)
        findNavController().navigate(action)
    }
}