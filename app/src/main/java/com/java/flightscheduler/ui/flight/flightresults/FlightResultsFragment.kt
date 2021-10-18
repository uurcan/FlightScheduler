package com.java.flightscheduler.ui.flight.flightresults

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.java.flightscheduler.BR
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.databinding.FragmentFlightResultsBinding
import com.java.flightscheduler.ui.base.observeOnce
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightResultsFragment : Fragment(), FlightResultsAdapter.FlightResultsListener {
    private lateinit var binding : FragmentFlightResultsBinding
    private lateinit var flightSearchAdapter : FlightResultsAdapter
    private val flightResultsViewModel: FlightSearchViewModel by viewModels()
    private val arguments by navArgs<FlightResultsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_flight_list,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeFlightHeader()
        initializeFlightResults()
    }

    private fun initializeFlightHeader() {
        binding.setVariable(BR.flightSearch,arguments.flightSearch)
        binding.executePendingBindings()
    }

    private fun initializeFlightResults() {
        val layoutManager = LinearLayoutManager(context)
        binding.rvFlightList.layoutManager = layoutManager
        binding.rvFlightList.setHasFixedSize(true)

        arguments.flightSearch.let {
            flightResultsViewModel.getFlightData(it)?.observeOnce { flightData ->
                if (flightData.isEmpty()) {
                    binding.txtFlightSearchErrorMessage.visibility = View.VISIBLE
                    binding.txtFlightSearchErrorMessage.text =
                        getString(R.string.text_no_flight_found)
                }
                flightSearchAdapter =
                    context?.let { it1 -> FlightResultsAdapter(flightData, it1, this) }!!
                binding.rvFlightList.adapter = flightSearchAdapter
            }
        }

        flightResultsViewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.pbFlightSearch.visibility = if (it) View.VISIBLE else View.GONE
        }

        flightResultsViewModel.errorLiveData?.observeOnce {
            binding.txtFlightSearchErrorMessage.visibility = View.VISIBLE
            binding.txtFlightSearchErrorMessage.text = it
        }
    }

    override fun onItemClick(view: View, item: FlightOffer) {
        val action = FlightResultsFragmentDirections.actionNavFlightResultsToFlightDetailsFragment(item)
        findNavController().navigate(action)
    }
}
