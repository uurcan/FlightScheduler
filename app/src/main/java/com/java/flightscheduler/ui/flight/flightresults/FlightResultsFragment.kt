package com.java.flightscheduler.ui.flight.flightresults

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.databinding.FragmentFlightResultsBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.ui.base.observeOnce
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightResultsFragment : BaseFragment<FlightSearchViewModel,FragmentFlightResultsBinding>(R.layout.fragment_flight_list){
    private lateinit var flightSearchAdapter : FlightResultsAdapter
    private val arguments by navArgs<FlightResultsFragmentArgs>()

    override fun init() {
    }

    override fun setViewModelFactory(): ViewModelProvider.Factory? {
        return defaultViewModelProviderFactory
    }

    override fun setViewModelClass(): Class<FlightSearchViewModel> =  FlightSearchViewModel::class.java

    override fun onBind() {
        initializeFlightHeader()
        initializeFlightResults()
    }

    private fun initializeFlightHeader() {
        binding?.setVariable(BR.flightSearch,arguments.flightSearch)
        binding?.executePendingBindings()
    }

    private fun initializeFlightResults() {
        val layoutManager = LinearLayoutManager(context)
        binding?.rvFlightList?.layoutManager = layoutManager
        binding?.rvFlightList?.setHasFixedSize(true)

        arguments.flightSearch.let { it ->
            viewModel?.getFlightData(it)?.observeOnce { flightData ->
                if (flightData.isEmpty()) {
                    binding?.txtFlightSearchErrorMessage?.visibility = View.VISIBLE
                    binding?.txtFlightSearchErrorMessage?.text =
                        getString(R.string.text_no_flight_found)
                }
                flightSearchAdapter = FlightResultsAdapter ({
                    switchToDetails(it)
                }, requireContext())
                flightSearchAdapter.submitList(flightData)
                binding?.rvFlightList?.adapter = flightSearchAdapter
            }
        }

        viewModel?.loadingLiveData?.observe(viewLifecycleOwner) {
            binding?.pbFlightSearch?.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel?.errorLiveData?.observeOnce {
            binding?.txtFlightSearchErrorMessage?.visibility = View.VISIBLE
            binding?.txtFlightSearchErrorMessage?.text = it
        }
    }

    private fun switchToDetails(it: FlightOffer) {
        val action = FlightResultsFragmentDirections.actionNavFlightResultsToFlightDetailsFragment(it)
        findNavController().navigate(action)
    }


}
