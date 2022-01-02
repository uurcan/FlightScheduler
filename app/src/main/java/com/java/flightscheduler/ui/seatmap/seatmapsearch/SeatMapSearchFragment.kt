package com.java.flightscheduler.ui.seatmap.seatmapsearch

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.seatmap.base.SeatMapSearch
import com.java.flightscheduler.databinding.SeatMapSearchBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.ui.base.MessageHelper
import com.java.flightscheduler.utils.extension.airportDropdownEvent
import com.java.flightscheduler.utils.extension.displayTimePicker
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeatMapSearchFragment : BaseFragment<SeatMapSearchViewModel, SeatMapSearchBinding>(R.layout.fragment_seat_map_search) {
    override val viewModel: SeatMapSearchViewModel by viewModels()

    override fun onBind() {
        initializeViews()
        initializeAirportDropdown()
    }

    private fun initializeViews() {
        binding?.layoutSeatMapDatePicker?.setOnClickListener {
            displayTimePicker(context, startForResult, true)
        }
        binding?.btnFlightSearchSeatMap?.setOnClickListener {
            saveSeatMapResults()
        }
        binding?.seatMapSearchViewModel = viewModel
    }

    override fun initializeDateParser(it: Intent) {
        val flightDate: String = it.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()

        viewModel.apply {
            onDateSelected(flightDate)
        }
    }

    private fun saveSeatMapResults() {
        val seatMapSearch = SeatMapSearch(
            origin = viewModel.origin.value,
            destination = viewModel.destination.value,
            flightDate = viewModel.flightDate.value!!,
            formattedFlightDate = binding?.txtSeatMapSearchDate?.text.toString(),
            legs = viewModel.legCount.value!!
        )

        if (areParamsValid(origin = viewModel.origin, destination = viewModel.destination)) {
            beginTransaction(seatMapSearch)
        }
    }

    private fun beginTransaction(seatMapSearch: SeatMapSearch) {
        val action = SeatMapSearchFragmentDirections.actionNavSeatMapSearchToSeatMapResults(seatMapSearch, null)
        findNavController().navigate(action)
    }

    private fun areParamsValid(origin: LiveData<Airport>, destination: LiveData<Airport>): Boolean {
        var isValid = true
        viewModel.performValidation(origin, destination).observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotBlank()) {
                MessageHelper.displayErrorMessage(view, errorMessage)
                isValid = false
            }
        }
        return isValid
    }

    private fun initializeAirportDropdown() {
        binding?.edtFlightSearchOrigin.let {
            it?.setOnItemClickListener { adapterView, _, position, _ ->
                viewModel.setOriginLiveData(
                    airportDropdownEvent(it, adapterView, position, false) as Airport
                )
            }
        }
        binding?.edtFlightSearchDestination.let {
            it?.setOnItemClickListener { adapterView, _, position, _ ->
                viewModel.setDestinationLiveData(
                    airportDropdownEvent(it, adapterView, position, true) as Airport
                )
            }
        }
    }
}