package com.java.flightscheduler.ui.seatmap.seatmapsearch

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.seatmap.base.SeatMapSearch
import com.java.flightscheduler.databinding.SeatMapSearchBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.ui.base.MessageHelper
import com.java.flightscheduler.ui.flight.flightroutes.FlightRoutesAdapter
import com.java.flightscheduler.ui.flight.flightroutes.FlightRoutesViewModel
import com.java.flightscheduler.utils.extension.airportDropdownEvent
import com.java.flightscheduler.utils.extension.displayTimePicker
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatMapSearchFragment : BaseFragment<SeatMapSearchViewModel,SeatMapSearchBinding>(R.layout.fragment_seat_map_search) {
    override val viewModel: SeatMapSearchViewModel? by viewModels()
    private val seatMapSearch : SeatMapSearch = SeatMapSearch()
    private val flightRoutesViewModel : FlightRoutesViewModel by viewModels()

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
        val flightDate : String = it.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()

        seatMapSearch.flightDate = flightDate
        viewModel?.apply {
            onDateSelected(flightDate)
        }
    }

    private fun saveSeatMapResults() {
        seatMapSearch.formattedFlightDate = binding?.txtSeatMapSearchDate?.text.toString()
        seatMapSearch.legs = binding?.txtFlightLegCount?.text.toString().toInt()

        if (areParamsValid(origin = seatMapSearch.originLocationCode, destination = seatMapSearch.destinationLocationCode)){
            beginTransaction(seatMapSearch)
        }
    }

    private fun beginTransaction(seatMapSearch: SeatMapSearch) {
        val action = SeatMapSearchFragmentDirections.actionNavSeatMapSearchToSeatMapResults(seatMapSearch)
        findNavController().navigate(action)
    }

    private fun areParamsValid(origin : String,destination : String): Boolean {
        var isValid = true
        viewModel?.performValidation(origin,destination)?.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotBlank()) {
                MessageHelper.displayErrorMessage(view,errorMessage)
                isValid = false
            }
        }
        return isValid
    }

    private fun initializeAirportDropdown() {
        flightRoutesViewModel.getIATACodes()?.observe(viewLifecycleOwner, {
            val adapter = FlightRoutesAdapter(requireContext(), it.toTypedArray())
            binding?.edtFlightSearchOrigin?.setAdapter(adapter)
            binding?.edtFlightSearchDestination?.setAdapter(adapter)}
        )
        binding?.edtFlightSearchOrigin.let {
            it?.setOnItemClickListener { adapterView, _, position, _ ->
                seatMapSearch.originLocationCode = airportDropdownEvent(it,adapterView,position,false)
            }
        }
        binding?.edtFlightSearchDestination.let {
            it?.setOnItemClickListener { adapterView, _, position, _ ->
                seatMapSearch.destinationLocationCode = airportDropdownEvent(it,adapterView,position,true)
            }
        }
    }
}