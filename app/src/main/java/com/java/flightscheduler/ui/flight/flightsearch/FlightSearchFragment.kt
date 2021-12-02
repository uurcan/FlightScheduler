package com.java.flightscheduler.ui.flight.flightsearch

import android.content.Intent
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.databinding.FragmentFlightSearchBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.ui.base.MessageHelper
import com.java.flightscheduler.utils.extension.airportDropdownEvent
import com.java.flightscheduler.utils.extension.displayTimePicker
import com.java.flightscheduler.utils.extension.swapRoutes
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightSearchFragment : BaseFragment<FlightSearchViewModel, FragmentFlightSearchBinding>(R.layout.fragment_flight_search) {
    override val viewModel: FlightSearchViewModel by viewModels()
    private val flightSearch: FlightSearch by lazy { FlightSearch() }

    override fun onBind() {
        initializeViews()
        initializeAirportDropdown()
    }

    private fun initializeAirportDropdown() {
        context?.let {
            viewModel.getIATACodes()?.observe(viewLifecycleOwner,
                {
                    val adapter = FlightSearchAdapter(requireContext(), it.toTypedArray())
                    binding?.edtFlightSearchOrigin?.setAdapter(adapter)
                    binding?.edtFlightSearchDestination?.setAdapter(adapter)
                }
            )
        }
        binding?.edtFlightSearchOrigin.let {
            it?.setOnItemClickListener { adapterView, _, position, _ ->
                flightSearch.origin = airportDropdownEvent(it, adapterView, position, false) as Airport
            }
        }
        binding?.edtFlightSearchDestination.let {
            it?.setOnItemClickListener { adapterView, _, position, _ ->
                flightSearch.destination = airportDropdownEvent(it, adapterView, position, true) as Airport
            }
        }
    }

    private fun saveFlightResults() {
        flightSearch.formattedDepartureDate = binding?.txtFlightSearchDepartureDate?.text.toString()
        flightSearch.formattedReturnDate = binding?.txtFlightSearchArrivalDate?.text.toString()
        flightSearch.adults = binding?.txtFlightAdultCount?.text.toString().toInt()
        flightSearch.children = binding?.txtFlightChildCount?.text.toString().toInt()
        flightSearch.audits = flightSearch.adults.plus(flightSearch.children)

        if (areFlightParamsValid(origin = flightSearch.origin.IATA,
                                destination = flightSearch.destination.IATA)) {
            viewModel.setFlightSearchLiveData(flightSearch)
            beginTransaction(flightSearch)
        }
    }

    private fun areFlightParamsValid(origin: String, destination: String): Boolean {
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

    override fun initializeDateParser(it: Intent) {
        val departureDate: String = it.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()
        val returnDate: String = it.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE).toString()

        flightSearch.departureDate = departureDate
        flightSearch.returnDate = returnDate
        viewModel.apply {
            onDateSelected(departureDate, returnDate)
        }
    }

    private fun initializeViews() {
        binding?.flightSearchViewModel = viewModel
        binding?.btnFlightOneWay?.setOnClickListener {
            oneWaySelection()
        }
        binding?.btnFlightRoundTrip?.setOnClickListener {
            roundTripSelection()
        }
        binding?.btnFlightSearchFlights?.setOnClickListener {
            saveFlightResults()
        }
        binding?.layoutFlightSearchRouteSwap?.setOnClickListener {
            swapRoutes(binding?.edtFlightSearchOrigin, binding?.edtFlightSearchDestination)
        }
        binding?.layoutFlightDeparturePicker?.setOnClickListener {
            displayTimePicker(context, startForResult, viewModel.isOneWay.value ?: false)
        }
    }

    private fun roundTripSelection() {
        flightSearch.isRoundTrip = true
        viewModel.onOneWaySelected(false)
        binding?.layoutFlightArrivalPicker?.visibility = VISIBLE
    }

    private fun oneWaySelection() {
        flightSearch.isRoundTrip = false
        flightSearch.returnDate = null
        viewModel.onOneWaySelected(true)
        binding?.layoutFlightArrivalPicker?.visibility = GONE
    }
}
