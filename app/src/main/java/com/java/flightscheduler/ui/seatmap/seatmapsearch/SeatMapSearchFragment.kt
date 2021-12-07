package com.java.flightscheduler.ui.seatmap.seatmapsearch

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.seatmap.base.SeatMapSearch
import com.java.flightscheduler.databinding.SeatMapSearchBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.ui.base.MessageHelper
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchAdapter
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchViewModel
import com.java.flightscheduler.utils.extension.airportDropdownEvent
import com.java.flightscheduler.utils.extension.displayTimePicker
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatMapSearchFragment : BaseFragment<SeatMapSearchViewModel, SeatMapSearchBinding>(R.layout.fragment_seat_map_search) {
    override val viewModel: SeatMapSearchViewModel? by viewModels()
    private val seatMapSearch: SeatMapSearch by lazy { SeatMapSearch() }
    private val flightSearchViewModel: FlightSearchViewModel by viewModels()

    override fun onBind() {
        initializeViews()
        initializeAirportDropdown()
    }

    private fun initializeViews() {
        binding?.seatMapSearchViewModel = viewModel
        binding?.layoutSeatMapDatePicker?.setOnClickListener {
            displayTimePicker(context, startForResult, true)
        }
        binding?.btnFlightSearchSeatMap?.setOnClickListener {
            saveSeatMapResults()
        }
    }

    override fun initializeDateParser(it: Intent) {
        val flightDate: String = it.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()

        seatMapSearch.flightDate = flightDate
        viewModel?.apply {
            onDateSelected(flightDate)
        }
    }

    private fun saveSeatMapResults() {
        seatMapSearch.formattedFlightDate = binding?.txtSeatMapSearchDate?.text.toString()
        seatMapSearch.legs = binding?.txtFlightLegCount?.text.toString().toInt()

        if (areParamsValid(origin = seatMapSearch.origin.IATA, destination = seatMapSearch.destination.IATA)) {
            beginTransaction(seatMapSearch)
        }
    }

    private fun beginTransaction(seatMapSearch: SeatMapSearch) {
        val action = SeatMapSearchFragmentDirections.actionNavSeatMapSearchToSeatMapResults(seatMapSearch = seatMapSearch, seatMapRequest = null)
        findNavController().navigate(action)
    }

    private fun areParamsValid(origin: String, destination: String): Boolean {
        var isValid = true
        viewModel?.performValidation(origin, destination)?.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotBlank()) {
                MessageHelper.displayErrorMessage(view, errorMessage)
                isValid = false
            }
        }
        return isValid
    }

    private fun initializeAirportDropdown() {
        flightSearchViewModel.getIATACodes()?.observe(viewLifecycleOwner, {
            val adapter = FlightSearchAdapter(requireContext(), it.toTypedArray())
            binding?.edtFlightSearchOrigin?.setAdapter(adapter)
            binding?.edtFlightSearchDestination?.setAdapter(adapter) }
        )
        binding?.edtFlightSearchOrigin.let {
            it?.setOnItemClickListener { adapterView, _, position, _ ->
                seatMapSearch.origin = airportDropdownEvent(it, adapterView, position, false) as Airport
            }
        }
        binding?.edtFlightSearchDestination.let {
            it?.setOnItemClickListener { adapterView, _, position, _ ->
                seatMapSearch.destination = airportDropdownEvent(it, adapterView, position, true) as Airport
            }
        }
    }
}