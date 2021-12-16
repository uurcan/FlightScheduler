package com.java.flightscheduler.ui.flight.flightresults

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.databinding.FragmentFlightResultsBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.utils.extension.observeOnce
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightResultsFragment : BaseFragment<FlightResultsViewModel, FragmentFlightResultsBinding>(R.layout.fragment_flight_list) {
    private lateinit var flightSearchAdapter: FlightResultsAdapter
    private val arguments by navArgs<FlightResultsFragmentArgs>()
    override val viewModel: FlightResultsViewModel by viewModels()

    override fun onBind() {
        initializeFlightHeader()
        initializeFlightResults()
    }

    private fun initializeFlightHeader() {
        binding?.setVariable(BR.flightSearch, arguments.flightSearch)
        binding?.executePendingBindings()
    }

    private fun initializeFlightResults() {
        binding?.rvFlightList?.setHasFixedSize(true)
        arguments.flightSearch.let { it ->
            viewModel.getFlightData(it)?.observeOnce { flightData ->
                if (flightData.isEmpty()) displayErrorMessage(getString(R.string.text_no_flight_found))

                flightSearchAdapter = FlightResultsAdapter({ switchToDetails(it) }, requireContext())
                flightSearchAdapter.submitList(flightData)
                binding?.rvFlightList?.adapter = flightSearchAdapter
            }
        }
    }

    private fun switchToDetails(it: FlightOffer) {
        val action = FlightResultsFragmentDirections.actionNavFlightResultsToFlightDetailsFragment(it)
        findNavController().navigate(action)
    }
}
