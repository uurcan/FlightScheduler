package com.java.flightscheduler.ui.flight.flightresults

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightOffer
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.databinding.FragmentFlightResultsBinding
import com.java.flightscheduler.ui.flight.flightdetail.FlightDetailsFragmentArgs
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchViewModel
import com.java.flightscheduler.utils.ParsingUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightResultsFragment : Fragment(), FlightResultsAdapter.FlightResultsListener {
    private lateinit var binding : FragmentFlightResultsBinding
    private val flightResultsViewModel : FlightResultsViewModel by viewModels()
    private val flightSearch by navArgs<FlightResultsFragmentArgs>()
    private lateinit var flightSearchAdapter : FlightResultsAdapter

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
        val originLocationCode : String = flightSearch.search.originLocationCode
        val destinationLocationCode : String = flightSearch.search.destinationLocationCode
        val departureDate : String = flightSearch.search.formattedDepartureDate
        val isOneWay : String = if (flightSearch.search.returnDate == null) getString(R.string.text_one_way) else getString(R.string.text_round_trip)
        val audits : String = flightSearch.search.children?.let { flightSearch.search.adults.plus(it) }.toString() + " " + getString(R.string.text_audits)
        val originLocationCity : String = ParsingUtils().crop(flightSearch.search.originLocationCity)
        val destinationLocationCity : String = ParsingUtils().crop(flightSearch.search.destinationLocationCity.toString())

        binding.txtFlightDetailOriginIata.text = originLocationCode
        binding.txtFlightDetailDestinationIata.text = destinationLocationCode
        binding.txtFlightDetailDepartureDate.text = departureDate
        binding.txtFlightDetailOneWay.text = isOneWay
        binding.txtFlightDetailAudits.text = audits
        binding.txtFlightDetailOriginCity.text = originLocationCity
        binding.txtFlightDetailDestinationCity.text = destinationLocationCity
    }

    private fun initializeFlightResults() {
        val layoutManager = LinearLayoutManager(context)
        binding.rvFlightList.layoutManager = layoutManager
        binding.rvFlightList.setHasFixedSize(true)

        flightResultsViewModel.getFlightData(flightSearch.search)?.observe(viewLifecycleOwner, { flightData ->
            if (flightData.isEmpty()) {
                binding.txtFlightSearchErrorMessage.visibility = View.VISIBLE
                binding.txtFlightSearchErrorMessage.text =
                    getString(R.string.text_no_flight_found)
            }
            flightSearchAdapter = context?.let { it1 -> FlightResultsAdapter(flightData, it1,this) }!!
            binding.rvFlightList.adapter = flightSearchAdapter
        })

        flightResultsViewModel.loadingLiveData.observe(viewLifecycleOwner) {
            binding.pbFlightSearch.visibility = if (it) View.VISIBLE else View.GONE
        }

        flightResultsViewModel.errorLiveData?.observe(viewLifecycleOwner) {
            binding.txtFlightSearchErrorMessage.visibility = View.VISIBLE
            binding.txtFlightSearchErrorMessage.text = it
        }
    }

    override fun onItemClick(view: View, item: FlightOffer) {
        val action = FlightResultsFragmentDirections.actionNavFlightResultsToFlightDetailsFragment(item)
        findNavController().navigate(action)
    }
}
