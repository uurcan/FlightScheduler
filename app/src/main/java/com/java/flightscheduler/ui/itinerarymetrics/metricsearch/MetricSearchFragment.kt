package com.java.flightscheduler.ui.itinerarymetrics.metricsearch

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.flight.FlightSearch
import com.java.flightscheduler.data.model.hotel.base.Language
import com.java.flightscheduler.data.model.metrics.Currency
import com.java.flightscheduler.data.model.metrics.MetricSearch
import com.java.flightscheduler.databinding.FragmentMetricsSearchBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.ui.flight.flightsearch.FlightSearchFragmentDirections
import com.java.flightscheduler.utils.MessageHelper
import com.java.flightscheduler.utils.extension.airportDropdownEvent
import com.java.flightscheduler.utils.extension.displayTimePicker
import com.java.flightscheduler.utils.extension.showListDialog
import com.java.flightscheduler.utils.extension.swapRoutes
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MetricSearchFragment : BaseFragment<MetricSearchViewModel,FragmentMetricsSearchBinding>(
    R.layout.fragment_metrics_search
) {
    override val viewModel: MetricSearchViewModel by viewModels()

    override fun onBind() {
        initializeViews()
        initializeAirportSelectedListener()
    }

    private fun initializeViews() {
        binding?.btnMetricsSearchMetricss?.setOnClickListener {
            saveFlightResults()
        }
        binding?.layoutMetricsSearchRouteSwap?.setOnClickListener {
            swapRoutes(binding?.edtMetricsSearchOrigin, binding?.edtMetricsSearchDestination)
        }
        binding?.layoutMetricsSort?.setOnClickListener {
            showListDialog(variable = AppConstants.CurrencyOptions, cancelable = true) { selected ->
                if (selected is Currency)
                    viewModel.setCurrencyClicked(selected)
            }
        }
        binding?.metricsSearchViewModel = viewModel
    }

    private fun initializeAirportSelectedListener() {
        binding?.edtMetricsSearchOrigin.let {
            it?.setOnItemClickListener { adapterView, _, position, _ ->
                viewModel.setOriginAirport(
                    airportDropdownEvent(it, adapterView, position, false) as Airport
                )
            }
        }
        binding?.edtMetricsSearchDestination.let {
            it?.setOnItemClickListener { adapterView, _, position, _ ->
                viewModel.setDestinationAirport(
                    airportDropdownEvent(it, adapterView, position, true) as Airport
                )
            }
        }
    }

    override fun initializeDateParser(it: Intent) {
        val departureDate: String = it.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()
        val returnDate: String = it.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_END_DATE).toString()

        viewModel.apply {
            onDateSelected(departureDate, returnDate)
        }
    }

    private fun saveFlightResults() {
        val metricSearch = MetricSearch(
            origin = viewModel.origin.value,
            destination = viewModel.destination.value,
            departureDate = binding?.txtFlightSearchDepartureNumber?.text.toString(),
            returnDate = if (viewModel.isOneWay.value == true) { null } else { viewModel.returnDate.value },
            currency = viewModel.currency.value
        )

        if (areFlightParamsValid(origin = viewModel.origin, destination = viewModel.destination, viewModel.currency)) {
            viewModel.setMetricSearchLiveData(metricSearch)
            beginTransaction(metricSearch)
        }
    }

    private fun areFlightParamsValid(origin: LiveData<Airport>, destination: LiveData<Airport>, currency: LiveData<Currency?>): Boolean {
        var isValid = true
        viewModel.performValidation(origin, destination, currency).observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotBlank()) {
                MessageHelper.displayErrorMessage(view, errorMessage)
                isValid = false
            }
        }
        return isValid
    }

    private fun beginTransaction(metricSearch: MetricSearch) {
        val action = MetricSearchFragmentDirections.actionNavMetricSearchToMetricResults(metricSearch)
        findNavController().navigate(action)
    }
}