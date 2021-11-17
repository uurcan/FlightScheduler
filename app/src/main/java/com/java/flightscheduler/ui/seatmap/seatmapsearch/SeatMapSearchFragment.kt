package com.java.flightscheduler.ui.seatmap.seatmapsearch

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.java.flightscheduler.R
import com.java.flightscheduler.data.model.flight.Airport
import com.java.flightscheduler.data.model.seatmap.base.SeatMapSearch
import com.java.flightscheduler.databinding.SeatMapSearchBinding
import com.java.flightscheduler.ui.base.BaseFragment
import com.java.flightscheduler.ui.base.MessageHelper
import com.java.flightscheduler.ui.flight.flightroutes.FlightRoutesAdapter
import com.java.flightscheduler.ui.flight.flightroutes.FlightRoutesViewModel
import com.java.flightscheduler.utils.flightcalendar.AirCalendarDatePickerActivity
import com.java.flightscheduler.utils.flightcalendar.AirCalendarIntent
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
        binding?.lifecycleOwner = viewLifecycleOwner
        binding?.seatMapSearchViewModel = viewModel

        binding?.layoutSeatMapDatePicker?.setOnClickListener { initializeDatePicker() }
        binding?.btnFlightSearchSeatMap?.setOnClickListener { saveSeatMapResults() }
    }

    private fun saveSeatMapResults() {
        seatMapSearch.formattedFlightDate = binding?.txtSeatMapSearchDate?.text.toString()
        seatMapSearch.legs = binding?.txtFlightLegCount?.text.toString().toInt()

        if (areParamsValid(origin = seatMapSearch.originLocationCode,
                destination = seatMapSearch.destinationLocationCode)){
            beginTransaction(seatMapSearch)
        }
    }

    private fun beginTransaction(seatMapSearch: SeatMapSearch) {
        val action = SeatMapSearchFragmentDirections.actionNavSeatMapSearchToSeatMapResults(seatMapSearch)
        findNavController().navigate(action)
    }

    private fun areParamsValid(origin : String,destination : String): Boolean {
        var isValid = true
        viewModel?.performValidation(origin,destination)?.observe(viewLifecycleOwner)
        { errorMessage ->
            if (errorMessage.isNotBlank()) {
                MessageHelper.displayErrorMessage(view,errorMessage)
                isValid = false
            }
        }
        return isValid
    }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.let {
                    initializeDateParser(it)
                }
            }
        }

    private fun initializeDateParser(intent: Intent) {
        val flightDate : String = intent.getStringExtra(AirCalendarDatePickerActivity.RESULT_SELECT_START_DATE).toString()

        seatMapSearch.flightDate = flightDate
        viewModel?.apply {
           onDateSelected(flightDate)
        }
    }

    private fun initializeDatePicker() {
        val intent = AirCalendarIntent(context)
        intent.setSelectButtonText(getString(R.string.text_select))
        intent.setResetBtnText(getString(R.string.text_reset))
        intent.isSingleSelect(true)
        intent.isMonthLabels(false)
        intent.setWeekDaysLanguage(AirCalendarIntent.Language.EN)
        startForResult.launch(intent)
    }

    private fun initializeAirportDropdown() {
        context?.let {
            flightRoutesViewModel.getIATACodes()?.observe(viewLifecycleOwner,
                {
                    val adapter = FlightRoutesAdapter(requireContext(), it.toTypedArray())
                    binding?.edtFlightSearchOrigin?.setAdapter(adapter)
                    binding?.edtFlightSearchDestination?.setAdapter(adapter)
                }
            )
        }
        binding?.edtFlightSearchOrigin?.setOnItemClickListener{ adapterView, _, i, _ ->
            val iataCode = adapterView.getItemAtPosition(i)
            if (iataCode is Airport) {
                val origin = "${iataCode.CITY} (${iataCode.IATA})"
                binding?.edtFlightSearchOrigin!!.setText(origin)
                seatMapSearch.originLocationCode = iataCode.IATA.toString()
                seatMapSearch.originLocationCity = iataCode.CITY.toString()
            }
        }
        binding?.edtFlightSearchDestination?.setOnItemClickListener{ adapterView, _, i, _ ->
            val iataCode = adapterView.getItemAtPosition(i)
            if (iataCode is Airport) {
                val destination = "${iataCode.CITY} (${iataCode.IATA})"
                binding?.edtFlightSearchDestination!!.setText(destination)
                seatMapSearch.destinationLocationCode = iataCode.IATA.toString()
                seatMapSearch.destinationLocationCity = iataCode.CITY.toString()
            }
        }
    }
}