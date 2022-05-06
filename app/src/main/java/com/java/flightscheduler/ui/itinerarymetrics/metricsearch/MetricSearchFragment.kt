package com.java.flightscheduler.ui.itinerarymetrics.metricsearch

import androidx.fragment.app.viewModels
import com.java.flightscheduler.R
import com.java.flightscheduler.data.constants.AppConstants
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.hotel.base.Language
import com.java.flightscheduler.data.model.metrics.Currency
import com.java.flightscheduler.databinding.FragmentMetricsSearchBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.utils.extension.airportDropdownEvent
import com.java.flightscheduler.utils.extension.displayTimePicker
import com.java.flightscheduler.utils.extension.showListDialog
import com.java.flightscheduler.utils.extension.swapRoutes
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
        }
        binding?.layoutMetricsSearchRouteSwap?.setOnClickListener {
            swapRoutes(binding?.edtMetricsSearchOrigin, binding?.edtMetricsSearchDestination)
        }
        binding?.layoutMetricsDatePicker?.setOnClickListener {
            displayTimePicker(context, startForResult, viewModel.isOneWay.value ?: true)
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
}