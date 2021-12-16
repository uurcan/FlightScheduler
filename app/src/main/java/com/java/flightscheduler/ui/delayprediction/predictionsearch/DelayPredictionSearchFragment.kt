package com.java.flightscheduler.ui.delayprediction.predictionsearch

import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airline
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.prediction.PredictionSearch
import com.java.flightscheduler.databinding.FragmentDelayPredictionSearchBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.ui.base.MessageHelper
import com.java.flightscheduler.utils.extension.airportDropdownEvent
import com.java.flightscheduler.utils.extension.displayTimePicker
import com.java.flightscheduler.utils.extension.initializeTimePicker
import com.java.flightscheduler.utils.extension.swapRoutes
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DelayPredictionSearchFragment : BaseFragment<DelayPredictionSearchViewModel,FragmentDelayPredictionSearchBinding>(R.layout.fragment_delay_prediction_search) {
    override val viewModel: DelayPredictionSearchViewModel by viewModels()

    override fun onBind() {
        initializeViews()
        initializeAirportSelectedListener()
    }

    private fun initializeViews() {
        binding?.btnPredictionSearchResults?.setOnClickListener {
            viewModel.setFlightNumber(Integer.parseInt(binding?.txtPredictionFlightNumber?.text.toString()))
            setPredictionInput()
        }
        binding?.layoutFlightSearchRouteSwap?.setOnClickListener {
            swapRoutes(binding?.edtFlightSearchOrigin, binding?.edtFlightSearchDestination)
        }
        binding?.layoutPredictionDatePicker?.setOnClickListener {
            displayTimePicker(context, startForResult, true)
        }
        binding?.layoutFlightDeparturePicker?.setOnClickListener {
            initializeTimePicker(binding?.txtPredictionDepartureTime)
        }
        binding?.layoutFlightArrivalPicker?.setOnClickListener {
            initializeTimePicker(binding?.txtPredictionArrivalTime)
        }
        binding?.predictionSearchViewModel = viewModel
    }

    private fun setPredictionInput() {
        val predictionSearch = PredictionSearch(
            origin = viewModel.origin.value,
            destination = viewModel.destination.value,
            departureDate = viewModel.flightDate.value,
            departureTime = viewModel.departureTime.value,
            arrivalTime = viewModel.arrivalTime.value,
            carrierCode = viewModel.carrier.value,
            flightNumber = viewModel.flightNumber.value
        )
        if (areFlightParamsValid(predictionSearch)) {
            beginTransaction(predictionSearch)
        }
    }
    override fun initializeDateParser(it: Intent) {
        val departureDate: String = it.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()

        viewModel.apply {
            onDateSelected(departureDate)
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
        binding?.edtPredictionCarrierCode.let {
            it?.setOnItemClickListener { adapterView, _, position, _ ->
                viewModel.setCarrier(
                    airportDropdownEvent(it, adapterView, position,true) as Airline
                )
            }
        }
    }

    private fun areFlightParamsValid(predictionSearch: PredictionSearch): Boolean {
        var isValid = true
        viewModel.performValidation(predictionSearch).observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotBlank()) {
                MessageHelper.displayErrorMessage(view, errorMessage)
                isValid = false
            }
        }
        return isValid
    }

    private fun beginTransaction(predictionSearch: PredictionSearch) {
        val action = DelayPredictionSearchFragmentDirections.actionNavPredictionSearchToPredictionResultsFragment(predictionSearch)
        findNavController().navigate(action)
    }
}