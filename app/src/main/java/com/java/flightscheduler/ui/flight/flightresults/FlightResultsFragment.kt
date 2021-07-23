package com.java.flightscheduler.ui.flight.flightresults

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.databinding.FragmentFlightResultsBinding
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightResultsFragment : Fragment() {
    private lateinit var binding : FragmentFlightResultsBinding
    private val flightSearchViewModel: FlightSearchViewModel by activityViewModels()
    private lateinit var flightSearchAdapter : FlightResultsAdapter
    private var flightSearch : FlightSearch? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_flight_list,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeFlightHeader()
        initializeFlightResults()
    }

    private fun initializeFlightHeader() {
        flightSearch = flightSearchViewModel.getFlightSearchLiveData()?.value
        val originLocationCode : String? = flightSearch?.originLocationCode
        val destinationLocationCode : String? = flightSearch?.destinationLocationCode
        val departureDate : String? = flightSearch?.formattedDepartureDate
        val isOneWay : String? = if (flightSearch?.returnDate == null) getString(R.string.text_one_way) else getString(R.string.text_round_trip)
        val audits : String? = flightSearch?.children?.let { flightSearch?.adults?.plus(it) }.toString() + " " + getString(R.string.text_audits)
        val originLocationCity : String? = flightSearch?.originLocationCity.toString()
        val destinationLocationCity : String? = flightSearch?.destinationLocationCity.toString()

        binding.txtFlightDetailOriginIata.text = originLocationCode
        binding.txtFlightDetailDestinationIata.text = destinationLocationCode
        binding.txtFlightDetailDepartureDate.text = departureDate
        binding.txtFlightDetailOneWay.text = isOneWay
        binding.txtFlightDetailAudits.text = audits.toString()
        binding.txtFlightDetailOriginCity.text = originLocationCity
        binding.txtFlightDetailDestinationCity.text = destinationLocationCity
    }

    private fun initializeFlightResults() {
        val layoutManager = LinearLayoutManager(context)
        binding.rvFlightList.layoutManager = layoutManager
        binding.rvFlightList.setHasFixedSize(true)

        flightSearch?.let {
            flightSearchViewModel.getFlightData(it)?.observe(viewLifecycleOwner, { flightData ->
                if (flightData.isEmpty()) {
                    binding.txtFlightSearchErrorMessage.visibility = View.VISIBLE
                    binding.txtFlightSearchErrorMessage.text =
                        getString(R.string.text_no_flight_found)
                }
                flightSearchAdapter = FlightResultsAdapter(flightData)
                binding.rvFlightList.adapter = flightSearchAdapter
            })
        }

        flightSearchViewModel.loadingLiveData.observe(viewLifecycleOwner, {
            binding.pbFlightSearch.visibility = if (it) View.VISIBLE else View.GONE
        })

        flightSearchViewModel.errorLiveData?.observe(viewLifecycleOwner, {
            binding.txtFlightSearchErrorMessage.visibility = View.VISIBLE
            binding.txtFlightSearchErrorMessage.text = it
        })
    }
}
