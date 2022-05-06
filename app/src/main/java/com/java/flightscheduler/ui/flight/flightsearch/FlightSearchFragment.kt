package com.java.flightscheduler.ui.flight.flightsearch

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.databinding.FragmentFlightSearchBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.utils.MessageHelper
import com.java.flightscheduler.utils.extension.airportDropdownEvent
import com.java.flightscheduler.utils.extension.displayTimePicker
import com.java.flightscheduler.utils.extension.swapRoutes
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightSearchFragment : BaseFragment<FlightSearchViewModel, FragmentFlightSearchBinding>(R.layout.fragment_flight_search) {
    override val viewModel: FlightSearchViewModel by viewModels()

    override fun onBind() {
        initializeViews()
        initializeAirportSelectedListener()
    }

    private fun initializeViews() {
        binding?.btnFlightSearchFlights?.setOnClickListener {
            saveFlightResults()
        }
        binding?.layoutFlightSearchRouteSwap?.setOnClickListener {
            swapRoutes(binding?.edtFlightSearchOrigin, binding?.edtFlightSearchDestination)
        }
        binding?.layoutFlightDeparturePicker?.setOnClickListener {
            displayTimePicker(context, startForResult, viewModel.isOneWay.value ?: false)
        }
        binding?.flightSearchViewModel = viewModel
    }

    override fun initializeDateParser(it: Intent) {
        val departureDate: String = it.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()
        val returnDate: String = it.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE).toString()

        viewModel.apply {
            onDateSelected(departureDate, returnDate)
        }
    }

    private fun initializeAirportSelectedListener() {
        binding?.edtFlightSearchOrigin.let {
            it?.setOnItemClickListener { adapterView, _, position, _ ->
                viewModel.setOriginAirport(
                    airportDropdownEvent(it, adapterView, position, false) as Airport
                )
            }
        }
        binding?.edtFlightSearchDestination.let {
            it?.setOnItemClickListener { adapterView, _, position, _ ->
                viewModel.setDestinationAirport(
                    airportDropdownEvent(it, adapterView, position, true) as Airport
                )
            }
        }
    }

    private fun saveFlightResults() {
        val flightSearch = FlightSearch(
            origin = viewModel.origin.value,
            destination = viewModel.destination.value,
            departureDate = viewModel.flightDate.value,
            returnDate = if (viewModel.isOneWay.value == true) { null } else { viewModel.returnDate.value },
            formattedDepartureDate = binding?.txtFlightSearchDepartureDate?.text.toString(),
            adults = viewModel.adultCount.value,
            children = viewModel.childCount.value,
            passengers = viewModel.adultCount.value!!.plus(viewModel.childCount.value!!)
        )

        if (areFlightParamsValid(origin = viewModel.origin, destination = viewModel.destination)) {
            viewModel.setFlightSearchLiveData(flightSearch)
            beginTransaction(flightSearch)
        }
    }

    private fun areFlightParamsValid(origin: LiveData<Airport>, destination: LiveData<Airport>): Boolean {
        var isValid = true
        viewModel.performValidation(origin, destination).observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotBlank()) {
                MessageHelper.displayErrorMessage(view, errorMessage)
                isValid = false
            }
        }
        return isValid
    }

    private fun beginTransaction(flightSearch: FlightSearch) {
        val action = FlightSearchFragmentDirections.actionNavFlightSearchToNavFlightResults(flightSearch)
        findNavController().navigate(action)
    }
}
