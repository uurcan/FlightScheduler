package com.java.flightscheduler.ui.flight.flightdetail

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.itineraries.Itinerary
import com.java.flightscheduler.data.model.flight.itineraries.SearchSegment
import com.java.flightscheduler.databinding.FragmentFlightDetailBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.ui.base.MessageHelper
import com.java.flightscheduler.utils.extension.showDialog
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FlightDetailsFragment : BaseFragment<FlightDetailsViewModel, FragmentFlightDetailBinding>
    (R.layout.fragment_flight_detail){
    private val args by navArgs<FlightDetailsFragmentArgs>()
    override val viewModel: FlightDetailsViewModel by viewModels()

    private val flightDetailsAdapter : FlightDetailsAdapter by lazy {
        FlightDetailsAdapter(args.offer,requireContext()) {
            onFlightSegmentSelected(it!!)
        }
    }

    private fun onFlightSegmentSelected(segment: SearchSegment) {
        viewModel.getFlightOfferTemplate(segment).observe(viewLifecycleOwner, {
            convertToJson(it)
        })
    }

    override fun onBind() {
        val flightOffer : FlightOffer = args.offer
        initializeFlightHeader(flightOffer)
        initializeFlightResults(flightOffer)
        setHasOptionsMenu(true)
    }

    private fun initializeFlightHeader(flightOffer: FlightOffer) {
        binding?.setVariable(BR.flightOfferHeader, flightOffer)
    }

    private fun initializeFlightResults(flightOffer: FlightOffer) {
        val layoutManager = LinearLayoutManager(context)
        binding?.rvFlightDetail?.layoutManager = layoutManager
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

    private fun convertToJson(flightOffer: FlightOffer){
        val moshi = Moshi.Builder().build()
        val adapter = moshi.adapter(Any::class.java)
        val before = "{\"meta\":{},\"data\":["
        val after = "],\"dictionaries\":{\"locations\":{}}}"
        val jsonStructure = adapter.toJson(flightOffer)
        val finalized = before + jsonStructure + after
        print(jsonStructure)
        val action = FlightDetailsFragmentDirections
            .actionNavFlightResultsToSeatMapFragment(seatMapSearch = null, seatMapRequest = finalized)
        findNavController().navigate(action)
    }
}